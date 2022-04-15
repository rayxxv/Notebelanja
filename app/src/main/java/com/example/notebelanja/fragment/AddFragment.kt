package com.example.notebelanja.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.notebelanja.ItemRepository
import com.example.notebelanja.R
import com.example.notebelanja.databinding.FragmentAddBinding
import com.example.notebelanja.room.Item
import com.example.notebelanja.room.ItemDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class AddFragment : DialogFragment(){
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    var mDB: ItemDatabase? = null
    lateinit var ItemRepository: ItemRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ItemRepository = ItemRepository(requireContext())
        binding.btnInput.setOnClickListener {

            when {
                binding.etNama.text.isNullOrEmpty() -> {
                    binding.wrapNama.error = "Nama barang belum dimasukan"
                }
                binding.etHarga.text.isNullOrEmpty() -> {
                    binding.wrapHarga.error = "Harga barang belum dimasukan"
                }
                binding.etJumlah.text.isNullOrEmpty() -> {
                    binding.wrapJumlah.error = "Jumlah barang belum dimasukan"
                }
                else -> {
                    val objectItem = Item(
                        null, binding.etNama.text.toString(), binding.etHarga.text.toString().toInt(), binding.etJumlah.text.toString().toInt()
                    )
                    GlobalScope.async {
                        val result = ItemRepository.insertItem(objectItem)
                        activity?.runOnUiThread {
                            if (result != 0.toLong()) {
                                Toast.makeText(requireContext(),"berhasil menambahkan note", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_addFragment_to_mainMenuFragment)
                            } else {
                                Toast.makeText(requireContext(),"Gagal menambahkan item ke daftar", Toast.LENGTH_SHORT).show()
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