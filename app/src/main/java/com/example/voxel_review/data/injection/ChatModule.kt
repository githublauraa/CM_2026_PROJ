package com.example.voxel_review.data.injection

import com.example.voxel_review.data.dataSource.ChatRemoteDataSource
import com.example.voxel_review.data.dataSource.impl.firestore.ChatFirestoreDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ChatModule {
    @Binds
    abstract fun bindChatRemoteDataSource(implementation: ChatFirestoreDataSourceImpl): ChatRemoteDataSource
}
