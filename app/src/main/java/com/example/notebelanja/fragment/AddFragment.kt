package com.example.notebelanja.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.notebelanja.databinding.FragmentAddBinding
import com.example.notebelanja.room.ItemDatabase

class AddFragment : DialogFragment(){
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    var mDB: ItemDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }


}