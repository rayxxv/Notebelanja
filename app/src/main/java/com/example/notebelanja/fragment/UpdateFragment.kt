package com.example.notebelanja.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.notebelanja.R
import com.example.notebelanja.databinding.FragmentUpdateBinding
import com.example.notebelanja.room.Item
import com.example.notebelanja.room.ItemDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class UpdateFragment() : DialogFragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private var mDb: ItemDatabase? = null
    lateinit var itemSelected : Item
    constructor(itemSelected:Item):this(){
        this.itemSelected = itemSelected
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = ItemDatabase.getInstance(requireContext())

        if(this::itemSelected.isInitialized){
            binding.tvTitle.text = "Update Harga ${itemSelected.nama_item}"
        }

        binding.btnUpdate.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_updateFragment)
            when {
                binding.etHarga.text.isNullOrEmpty() -> {
                    binding.wrapHarga.error = "Harga barang belum dimasukan"
                }
                binding.etJumlah.text.isNullOrEmpty() -> {
                    binding.wrapJumlah.error = "Jumlah barang belum dimasukan"
                }
                else -> {
                    val harga: Int = binding.etHarga.text.toString().toInt()
                    val jumlah: Int = binding.etJumlah.text.toString().toInt()

                    val objectItem = itemSelected
                    objectItem.harga_barang = harga
                    objectItem.jumlah_barang = jumlah

                    GlobalScope.async {
                        val result = mDb?.itemDao()?.updateItem(objectItem)
                        activity?.runOnUiThread {
                            if (result != 0) {
                                Toast.makeText(it.context, "Note  berhasil terupdate", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_updateFragment_to_mainMenuFragment)
                            }
                            else {
                                Toast.makeText(it.context, "Noted gagal diubah", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    dismiss()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}