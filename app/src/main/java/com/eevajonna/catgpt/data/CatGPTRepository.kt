package com.eevajonna.catgpt.data

import kotlinx.coroutines.flow.Flow

interface CatGPTRepository {
    suspend fun getMessages(): Flow<List<Message>>

    suspend fun addMessage(message: Message)

    suspend fun deleteMessage(message: Message)

    suspend fun updateMessage(message: Message)
}

class RepositoryImpl(private val dao: CatGPTDao) : CatGPTRepository {
    override suspend fun getMessages(): Flow<List<Message>> = dao.getMessages()

    override suspend fun addMessage(message: Message) = dao.addMessage(message)

    override suspend fun deleteMessage(message: Message) = dao.deleteMessage(message)

    override suspend fun updateMessage(message: Message) = dao.updateMessage(message)
}
