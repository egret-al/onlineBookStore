package com.rkc.onlinebookstore.view.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.view.home.MainHomeActivity
import com.rkc.onlinebookstore.viewmodel.login.*
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(LoginViewModel::class.java)
        btnLogin.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            loginViewModel.login(editTextUsername.text.toString(), editTextPassword.text.toString())
        }
        btnRegister.setOnClickListener { NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_registerFragment) }
        forgotPassword.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment) }

        loginViewModel.loginStatus.observe(viewLifecycleOwner, {
            //观察登录状态，以在view层的视图做出不同的响应
            when(it) {
                LOGIN_FAILURE -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, "账号或密码错误！", Toast.LENGTH_SHORT).show()
                }
                LOGIN_SUCCESS -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, "登录成功！", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireContext(), MainHomeActivity::class.java))
                }
                REQUESTING -> progressBar.visibility = View.VISIBLE
                NET_ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, "网络异常，请稍后再试", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}