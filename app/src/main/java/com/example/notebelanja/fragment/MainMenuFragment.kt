package com.example.notebelanja.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notebelanja.MainActivity
import com.example.notebelanja.R
import com.example.notebelanja.databinding.FragmentMainMenuBinding
import com.example.notebelanja.room.ItemAdapter
import com.example.notebelanja.room.ItemDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainMenuFragment : Fragment() {

    private var mDb: ItemDatabase? = null
    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ItemAdapter
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = requireContext().getSharedPreferences(LoginFragment.AKUN, Context.MODE_PRIVATE)
        binding.tvWelcome.text = "Welcome ${preferences.getString(LoginFragment.USERNAME,null)}"

        mDb = ItemDatabase.getInstance(requireContext())
        adapter = ItemAdapter()
        binding.rvList.adapter = adapter
        binding.rvList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        fetchData()
        logout()

        binding.fabNewItem.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_addFragment)
        }
    }
    fun fetchData(){
        GlobalScope.launch {
            val listItem = mDb?.itemDao()?.getAllItem()
            activity?.runOnUiThread {
                listItem?.let {
                    adapter.setData(it)
                }
            }
        }
    }
    fun logout(){
        binding.tvLogout.setOnClickListener {
            val alert = AlertDialog.Builder(requireContext())
            alert.apply{
                setTitle("Logout")
                setMessage("Apakah anda yakin ingin log out?")
                setNegativeButton("Batal"){dialog,which->
                    dialog.dismiss()
                }
                setPositiveButton("Logout"){dialog,which->
                    dialog.dismiss()

                    preferences.edit().clear().apply()
                    findNavController().navigate(R.id.action_mainMenuFragment_to_loginFragment)
                }
            }
            alert.show()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    override fun onResume() {
        super.onResume()
        fetchData()
    }

}