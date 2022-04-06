package com.example.notebelanja.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.notebelanja.R
import com.example.notebelanja.databinding.FragmentRegisterBinding
import com.example.notebelanja.room.ItemDatabase
import com.example.notebelanja.room.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class RegisterFragment : Fragment() {
    private var mDB:ItemDatabase?=null
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val USERNAME = "username"
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDB = ItemDatabase.getInstance(requireContext())

        binding.btnDaftar.setOnClickListener {
            when {
                binding.etUsername.text.isNullOrEmpty() -> {
                    binding.wrapUsername.error = "Silahkan masukan username anda !"
                }
                binding.etEmail.text.isNullOrEmpty() -> {
                    binding.wrapEmail.error = "Silahkan masukan email anda !"
                }
                binding.etPassword.text.isNullOrEmpty() -> {
                    binding.wrapPassword.error = "Silahkan masukan password anda !"
                }
                binding.etConfirmPassword.text.isNullOrEmpty() -> {
                    binding.wrapConfirmPassword.error = "Silahkan konfirmasi password anda !"
                }
                binding.etPassword.text.toString() != binding.etConfirmPassword.text.toString() -> {
                    binding.wrapConfirmPassword.error = "Password anda tidak sesuai !"
                    binding.etConfirmPassword.setText("")
                }
                else -> {
                    val User = User(
                        null,
                        binding.etUsername.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                    GlobalScope.async {
                        val result =mDB?.userDao()?.addUser(User)
                        activity?.runOnUiThread {
                            if (result != 0.toLong()){
                                Toast.makeText(activity, "Pendaftaran akun anda berhasil", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(activity, "Pendaftaran gagal", Toast.LENGTH_SHORT).show()
                            }
                            onStop()
                        }
                    }
                    val username = binding.etUsername.text.toString()
                    val bundle = Bundle().apply {
                        putString(USERNAME, username)
                    }
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment2, bundle)
                }
            }
        }
        binding.btnMasuk.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}