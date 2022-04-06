package com.example.notebelanja.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Item::class], version = 1)
abstract class ItemDatabase():RoomDatabase() {
    abstract fun storeDao() : UserDao
    abstract fun ItemDao() : ItemDao

    companion object{
        private var INSTANCE : ItemDatabase? = null

        fun getInstance(context: Context): ItemDatabase? {
            if (INSTANCE == null){
                synchronized(ItemDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDatabase::class.java,"Noted.db"
                    ).build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}