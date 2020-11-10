package com.rkc.onlinebookstore.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.viewmodel.login.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(LoginViewModel::class.java)
        btnLogin.setOnClickListener { loginViewModel.login(requireContext(), editTextUsername.text.toString(), editTextPassword.text.toString()) }
        btnRegister.setOnClickListener { NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_registerFragment) }
    }

    override fun onDestroy() {
        super.onDestroy()
        loginViewModel.destroyHandler()
    }
}