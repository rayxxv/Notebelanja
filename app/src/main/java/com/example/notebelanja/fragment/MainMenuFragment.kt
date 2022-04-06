package com.example.notebelanja.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notebelanja.databinding.FragmentMainMenuBinding
import com.example.notebelanja.room.ItemAdapter
import com.example.notebelanja.room.ItemDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainMenuFragment : Fragment(){
    private var mDB: ItemDatabase? = null
    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        fetchData()
    }

    fun fetchData(){
        GlobalScope.launch {
            val listItem = mDB?.itemDao()?.getAllItem()
            activity?.runOnUiThread{
                listItem?.let {
                    val adapter = ItemAdapter(it)
                    binding.recyclerView.adapter = adapter
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ItemDatabase.destroyInstance()
    }

}