package com.example.notebelanja.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity (tableName = "item_table")
@Parcelize
data class Item(
    @PrimaryKey(autoGenerate = true) var id:Int?,
    @ColumnInfo(name = "nama_item") var nama_item : String,
    @ColumnInfo(name = "harga_barang") var harga_barang : Int,
    @ColumnInfo(name = "jumlah_barang") var jumlah_barang : Int
): Parcelable