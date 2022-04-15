package com.example.notebelanja

import android.content.Context
import androidx.annotation.WorkerThread
import com.example.notebelanja.room.Item
import com.example.notebelanja.room.ItemDatabase

class ItemRepository(context: Context) {

    private val myDB = ItemDatabase.getInstance(context)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllItem(): List<Item>? {
        return myDB?.itemDao()?.getAllItem()
    }

    suspend fun insertItem(item: Item): Long? {
        return myDB?.itemDao()?.insertItem(item)
    }

    suspend fun updateItem(item: Item): Int? {
        return myDB?.itemDao()?.updateItem(item)
    }

    suspend fun deleteItem(item: Item): Int? {
        return myDB?.itemDao()?.deleteItem(item)
    }

}