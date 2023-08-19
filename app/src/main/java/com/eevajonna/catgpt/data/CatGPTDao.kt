package com.eevajonna.catgpt.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CatGPTDao {
    @Query("SELECT * FROM Message")
    fun getMessages(): Flow<List<Message>>

    @Insert
    fun addMessage(message: Message)

    @Delete
    fun deleteMessage(message: Message)

    @Update
    fun updateMessage(message: Message)
}
