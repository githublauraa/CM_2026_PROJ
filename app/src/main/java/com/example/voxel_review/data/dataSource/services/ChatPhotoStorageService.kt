package com.example.voxel_review.data.dataSource.services

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.IOException

class ChatPhotoStorageService @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val storage: FirebaseStorage
) {
    suspend fun upload(path: String, capture: Uri) {
        val bytes = withContext(Dispatchers.IO) { preparePhoto(capture) }
        val metadata = StorageMetadata.Builder().setContentType("image/jpeg").build()
        val upload = storage.reference.child(path).putBytes(bytes, metadata)
        try {
            upload.await()
        } catch (error: CancellationException) {
            upload.cancel()
            throw error
        }
    }

    suspend fun download(path: String): Bitmap = withContext(Dispatchers.IO) {
        val bytes = storage.reference.child(path).getBytes(MAX_BYTES.toLong()).await()
        val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
        options.inSampleSize = sampleSize(options)
        options.inJustDecodeBounds = false
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
            ?: throw IOException("Foto ilegible")
    }

    suspend fun removeUpload(path: String) {
        storage.reference.child(path).delete().await()
    }

    fun discardCapture(uri: Uri) {
        if (uri.authority == "${context.packageName}.chat.camera") {
            context.contentResolver.delete(uri, null, null)
        }
    }

    private fun preparePhoto(uri: Uri): ByteArray {
        val resolver = context.contentResolver
        val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        resolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, options) }
        options.inSampleSize = sampleSize(options)
        options.inJustDecodeBounds = false

        val image = resolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, options) }
            ?: throw IOException("Foto ilegible")
        try {
            val orientation = resolver.openInputStream(uri)?.use { stream ->
                ExifInterface(stream).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            }
            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
                ExifInterface.ORIENTATION_FLIP_VERTICAL -> matrix.setScale(1f, -1f)
                ExifInterface.ORIENTATION_TRANSPOSE -> {
                    matrix.setRotate(90f)
                    matrix.postScale(-1f, 1f)
                }
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
                ExifInterface.ORIENTATION_TRANSVERSE -> {
                    matrix.setRotate(-90f)
                    matrix.postScale(-1f, 1f)
                }
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(-90f)
            }

            val oriented = Bitmap.createBitmap(image, 0, 0, image.width, image.height, matrix, true)
            try {
                return ByteArrayOutputStream().use { output ->
                    if (!oriented.compress(Bitmap.CompressFormat.JPEG, 82, output) || output.size() > MAX_BYTES) {
                        throw IOException("No se pudo preparar la foto")
                    }
                    output.toByteArray()
                }
            } finally {
                if (oriented !== image) oriented.recycle()
            }
        } finally {
            image.recycle()
        }
    }

    private fun sampleSize(options: BitmapFactory.Options): Int {
        val largestSide = maxOf(options.outWidth, options.outHeight)
        var sample = 1
        while (largestSide / sample > 1600) {
            sample *= 2
        }
        return sample
    }

    private companion object {
        const val MAX_BYTES = 5 * 1024 * 1024
    }
}
