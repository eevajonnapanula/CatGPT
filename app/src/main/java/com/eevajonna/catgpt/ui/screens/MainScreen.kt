package com.eevajonna.catgpt.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.eevajonna.catgpt.data.Message
import com.eevajonna.catgpt.ui.CatGPTViewModel

@Composable
fun MainScreen(viewModel: CatGPTViewModel) {
    LaunchedEffect(Unit) {
        viewModel.getMessages()
    }

    fun sendMessage(text: String) {
        viewModel.sendMessage(text)
    }

    fun deleteMessage(message: Message) {
        viewModel.deleteMessage(message)
    }

    fun updateMessage(message: Message) {
        viewModel.updateMessage(message)
    }

    ChatScreen(viewModel.messages, viewModel.catTyping, ::sendMessage, ::deleteMessage, ::updateMessage)
}
