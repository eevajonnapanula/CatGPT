package com.eevajonna.catgpt.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.eevajonna.catgpt.data.AppDatabase
import com.eevajonna.catgpt.data.CatGPTRepository
import com.eevajonna.catgpt.data.Message
import com.eevajonna.catgpt.data.RepositoryImpl
import com.eevajonna.catgpt.data.Sender
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class CatGPTViewModel(application: Application) : ViewModel() {
    var messages by mutableStateOf(emptyList<Message>())
    var catTyping by mutableStateOf(false)

    private val repository: CatGPTRepository

    init {
        val db = AppDatabase.getDatabase(application)
        val dao = db.getDao()

        repository = RepositoryImpl(dao)
    }

    fun getMessages() {
        viewModelScope.launch {
            repository.getMessages().collect { messagesList ->
                messages = messagesList.map { it }
            }
        }
    }

    fun sendMessage(text: String) {
        val message = Message(
            text = text,
            sender = Sender.ME,
        )

        val catMessage = generateCatMessage(text)

        viewModelScope.launch(Dispatchers.IO) {
            repository.addMessage(message)
            catTyping = true
            delay(1090L)
            repository.addMessage(catMessage)
            catTyping = false
        }
    }

    fun deleteMessage(message: Message) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMessage(message)
        }
    }

    fun updateMessage(message: Message) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMessage(message)
        }
    }

    private fun generateCatMessage(incomingMessage: String): Message {
        val containsCatnip = incomingMessage.lowercase().contains("catnip")

        val catMessage = if (containsCatnip) "MEOW" else getMeowString()

        return Message(
            text = catMessage,
            sender = Sender.CAT,
        )
    }

    private fun getMeowString(): String {
        return List(Random.nextInt(1, 20)) {}
            .joinToString(" ", postfix = ".") {
                "meow"
            }.replaceFirstChar { "M" }
    }
}

class CatGPTViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CatGPTViewModel(application) as T
    }
}
