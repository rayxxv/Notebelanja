package com.example.notebelanja.room

import androidx.room.*

@Dao
interface ItemDao {
    @Query("SELECT * FROM Item")
    fun getAllItem():List<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(student: Item):Long

    @Update
    fun updateItem(student: Item):Int

    @Delete
    fun deleteItem(student: Item):Int

}