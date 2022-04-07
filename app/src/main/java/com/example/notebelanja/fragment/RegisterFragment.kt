package com.example.notebelanja.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.notebelanja.R
import com.example.notebelanja.databinding.FragmentRegisterBinding
import com.example.notebelanja.room.ItemDatabase
import com.example.notebelanja.room.User
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@DelicateCoroutinesApi
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
    )
    : View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDB = ItemDatabase.getInstance(requireContext())

        binding.btnDaftar.setOnClickListener {
            when {
                binding.etUsername.text.isNullOrEmpty() || binding.etEmail.text.isNullOrEmpty() || binding.etPassword.text.isNullOrEmpty() || binding.etConfirmPassword.text.isNullOrEmpty() ->{
                    Toast.makeText(activity, "Terdapat Data Yang Belum Terisi", Toast.LENGTH_SHORT).show()
                }
                binding.etPassword.text.toString() != binding.etConfirmPassword.text.toString() -> {
                    Toast.makeText(activity, "Password anda tidak sesuai", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val user = User(
                        null,
                        binding.etUsername.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                    GlobalScope.async {
                        val result =mDB?.userDao()?.addUser(user)
                        activity?.runOnUiThread {
                            if (result != 0.toLong()){
                                Toast.makeText(activity, "Pendaftaran akun anda berhasil", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(activity, "Pendaftaran akun anda gagal", Toast.LENGTH_SHORT).show()
                            }
                            onStop()
                        }
                    }

                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment2)
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