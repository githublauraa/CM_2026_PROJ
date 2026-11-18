package com.example.voxel_review.data.dataSource.services

import com.example.voxel_review.ChatNotificationTarget
import jakarta.inject.Inject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Singleton

@Singleton
class ForegroundChatNotifications @Inject constructor() {
    private val channel = Channel<ChatNotificationTarget>(
        capacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val messages = channel.receiveAsFlow()

    @Volatile
    var isAppResumed = false

    fun showIfForeground(target: ChatNotificationTarget): Boolean {
        return isAppResumed && channel.trySend(target).isSuccess
    }
}
