package com.example.notebelanja.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.notebelanja.R
import com.example.notebelanja.databinding.FragmentLoginBinding
import com.example.notebelanja.room.ItemDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class LoginFragment : Fragment() {
    private var mDB: ItemDatabase?= null
    private var _binding: FragmentLoginBinding?= null
    private val binding get() = _binding!!

    companion object{
        const val AKUN = "user_login"
        const val USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    )
    : View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDB = ItemDatabase.getInstance(requireContext())

        val loginscreen: SharedPreferences = requireActivity().getSharedPreferences(AKUN, Context.MODE_PRIVATE)
        if (loginscreen!!.getString(USERNAME,null)!=null){
            findNavController().navigate(R.id.action_loginFragment_to_mainMenuFragment)

        }

        binding.btnDaftar.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment3)
        }

        binding.btnMasuk.setOnClickListener {
            GlobalScope.async {
                val flags = mDB?.userDao()?.login(binding.etUsername.text.toString(), binding.etPassword.text.toString())
                activity?.runOnUiThread {
                    if (flags == true) {
                        val sharpref: SharedPreferences.Editor = loginscreen.edit()
                        findNavController().navigate(R.id.action_loginFragment_to_mainMenuFragment)
                    } else {
                        Toast.makeText(context, "Tidak Berhasil Login", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}