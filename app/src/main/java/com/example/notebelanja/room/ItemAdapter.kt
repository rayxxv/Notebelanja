package com.example.notebelanja.room

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.notebelanja.MainActivity
import com.example.notebelanja.databinding.ItemListBinding
import com.example.notebelanja.fragment.MainMenuFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    private val listItem = mutableListOf<Item>()

    class ViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            tvNamaBarang.text = listItem[position].nama_item
            tvHargaBarang.text = ": ${listItem[position].harga_barang.toString()}"
            tvJumlahBarang.text = ": ${listItem[position].jumlah_barang.toString()}"


            btnDelete.setOnClickListener {
                AlertDialog.Builder(it.context)
                    .setPositiveButton("Ya"){p0,p1 ->
                        val mDb = ItemDatabase.getInstance(holder.itemView.context)
                        GlobalScope.async {
                            val result = mDb?.itemDao()?.deleteItem(listItem[position])
                            (holder.itemView.context as MainActivity).runOnUiThread {
                                if (result != 0 ){
                                    Toast.makeText(it.context, "${listItem[position].nama_item} berhasil dihapus", Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(it.context, "${listItem[position].nama_item} gagal dihapus", Toast.LENGTH_SHORT).show()
                                }
                            }
                            (holder.itemView.context as MainMenuFragment).fetchData()
                        }
                    }
                    .setNegativeButton("Batal"){p0,p1->
                        p0.dismiss()
                    }
                    .setTitle("Konfirmasi Hapus")
                    .setMessage("Apakah anda yakin ingin menghapus data ${listItem[position].nama_item}")
                    .create()
                    .show()
            }
        }
    }

    override fun getItemCount(): Int = listItem.size
    fun setData(itemList: List<Item>) {
        listItem.clear()
        listItem.addAll(itemList)
        notifyDataSetChanged()
    }
}