package com.example.voxel_review.repository

import android.net.Uri
import com.example.voxel_review.data.dataSource.CachedChatNotification
import com.example.voxel_review.data.dataSource.ChatNotificationCache
import com.example.voxel_review.data.dataSource.ChatRemoteDataSource
import com.example.voxel_review.data.dtos.ChatContactDto
import com.example.voxel_review.data.dataSource.services.ChatPhotoStorageService
import com.example.voxel_review.data.info.chat.ChatCursor
import com.example.voxel_review.data.info.chat.ChatPage
import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.data.repository.ChatRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException
import java.time.Instant

class ChatRepositoryTest {
    private val remote = mockk<ChatRemoteDataSource>()
    private val auth = mockk<AuthRepository>()
    private val photos = mockk<ChatPhotoStorageService>()
    private val cache = mockk<ChatNotificationCache>()
    private val repository = ChatRepository(remote, auth, photos, cache)

    @Test
    fun ambosParticipantesEscribenEnElMismoChat() = runTest {
        coEvery { remote.sendMessage(any(), any(), any(), any()) } returns Unit

        every { auth.currentUser?.uid } returns "usuarioB"
        assertThat(repository.sendMessage("usuarioA", " Hola ").isSuccess).isTrue()

        every { auth.currentUser?.uid } returns "usuarioA"
        assertThat(repository.sendMessage("usuarioB", "Hola de vuelta").isSuccess).isTrue()

        val participants = listOf("usuarioA", "usuarioB")
        coVerify(exactly = 1) {
            remote.sendMessage("usuarioA~usuarioB", participants, "usuarioB", "Hola")
        }
        coVerify(exactly = 1) {
            remote.sendMessage("usuarioA~usuarioB", participants, "usuarioA", "Hola de vuelta")
        }
    }

    @Test
    fun impideElAutoenvioYLosMensajesVacios() = runTest {
        every { auth.currentUser?.uid } returns "usuarioA"

        assertThat(repository.sendMessage("usuarioA", "Hola").isFailure).isTrue()
        assertThat(repository.sendMessage("usuarioB", "  ").isFailure).isTrue()
        coVerify(exactly = 0) { remote.sendMessage(any(), any(), any(), any()) }
    }

    @Test
    fun cargaLaPaginaAnteriorConElCursorDelChat() = runTest {
        every { auth.currentUser?.uid } returns "usuarioA"
        val cursor = ChatCursor("ultimoMensaje", Instant.EPOCH)
        coEvery { remote.getMessagesBefore("usuarioA~usuarioB", cursor) } returns ChatPage(emptyList(), null)

        assertThat(repository.loadOlderMessages("usuarioB", cursor).getOrThrow().items).isEmpty()
        coVerify(exactly = 1) { remote.getMessagesBefore("usuarioA~usuarioB", cursor) }
    }

    @Test
    fun entregaElErrorDelListenerEnEspanol() = runTest {
        every { auth.currentUser?.uid } returns "usuarioA"
        val error = IOException("Network unavailable")
        every { remote.observeMessages("usuarioA~usuarioB") } returns flow { throw error }

        val failure = repository.observeMessages("usuarioB").first().exceptionOrNull()

        assertThat(failure?.message).isEqualTo("No se pudieron cargar los mensajes. Inténtalo de nuevo.")
        assertThat(failure?.cause).isSameInstanceAs(error)
    }
    @Test
    fun subeLaFotoAntesDeGuardarSuReferencia() = runTest {
        val capture = mockk<Uri>()
        val chatId = "usuarioA~usuarioB"
        val messageId = "12345678901234567890"
        val path = "chatPhotos/$chatId/usuarioA/$messageId.jpg"
        val participants = listOf("usuarioA", "usuarioB")
        every { auth.currentUser?.uid } returns "usuarioA"
        every { remote.newMessageId(chatId) } returns messageId
        coEvery { photos.upload(path, capture) } returns Unit
        coEvery { remote.sendPhotoMessage(chatId, participants, "usuarioA", messageId, path) } returns Unit

        assertThat(repository.sendPhoto("usuarioB", capture).isSuccess).isTrue()

        coVerifyOrder {
            photos.upload(path, capture)
            remote.sendPhotoMessage(chatId, participants, "usuarioA", messageId, path)
        }
    }

    @Test
    fun eliminaLaSubidaSiFirestoreRechazaElMensaje() = runTest {
        val capture = mockk<Uri>()
        val chatId = "usuarioA~usuarioB"
        val messageId = "12345678901234567890"
        val path = "chatPhotos/$chatId/usuarioA/$messageId.jpg"
        every { auth.currentUser?.uid } returns "usuarioA"
        every { remote.newMessageId(chatId) } returns messageId
        coEvery { photos.upload(path, capture) } returns Unit
        coEvery { remote.sendPhotoMessage(chatId, any(), "usuarioA", messageId, path) } throws IOException()
        coEvery { photos.removeUpload(path) } returns Unit

        assertThat(repository.sendPhoto("usuarioB", capture).exceptionOrNull()?.message)
            .isEqualTo("No se pudo enviar la foto. Inténtalo de nuevo.")
        coVerify(exactly = 1) { photos.removeUpload(path) }
    }


    @Test
    fun muestraUnAvisoRecibidoAunqueFalleElHistorialRemoto() = runTest {
        every { cache.observe("destinatario") } returns flowOf(
            listOf(CachedChatNotification("chat_mensaje", "autor", 100))
        )
        every { remote.observeNotifications("destinatario") } returns flow { throw IOException() }
        coEvery { remote.getContact("autor") } returns ChatContactDto(username = "Ana")

        val notice = repository.observeNotifications("destinatario")
            .first { it.getOrNull()?.isNotEmpty() == true }
            .getOrThrow()
            .single()

        assertThat(notice.title).isEqualTo("Ana")
        assertThat(notice.chatSenderId).isEqualTo("autor")
        assertThat(notice.message).isEqualTo("Te envió un mensaje")
    }

}
