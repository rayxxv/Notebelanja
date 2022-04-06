package com.example.notebelanja.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notebelanja.databinding.ItemListBinding

class ItemAdapter(private val listItem : List<Item>): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    class ViewHolder(val binding : ItemListBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding){
            tvNamaBarang.text = listItem[position].nama_item
            tvHargaBarang.text = ": ${listItem[position].harga_barang.toString()}"
            tvJumlahBarang.text = ": ${listItem[position].jumlah_barang.toString()}"
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

}