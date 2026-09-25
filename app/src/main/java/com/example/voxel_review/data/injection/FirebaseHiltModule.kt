package com.example.voxel_review.data.injection

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

@Module
@InstallIn(SingletonComponent::class)
object FirebaseHiltModule {

    @Provides
    fun auth(): FirebaseAuth = Firebase.auth


    @Provides
    fun storage(): FirebaseStorage = Firebase.storage
}