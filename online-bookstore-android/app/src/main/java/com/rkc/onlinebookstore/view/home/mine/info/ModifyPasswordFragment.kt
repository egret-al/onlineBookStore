package com.rkc.onlinebookstore.view.home.mine.info

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.viewmodel.home.mine.info.ModifyPasswordViewModel
import com.rkc.onlinebookstore.viewmodel.home.mine.info.PASSWORD_FAILURE
import com.rkc.onlinebookstore.viewmodel.home.mine.info.PASSWORD_SUCCESS
import com.rkc.onlinebookstore.viewmodel.home.mine.info.REQUEST_ERROR
import com.rkc.onlinebookstore.viewmodel.login.USER
import com.rkc.onlinebookstore.viewmodel.login.USERNAME
import kotlinx.android.synthetic.main.common_title.*
import kotlinx.android.synthetic.main.fragment_modify_password.*

class ModifyPasswordFragment : Fragment() {
    private lateinit var modifyPasswordViewModel: ModifyPasswordViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_modify_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        titleTV.text = "修改密码"
        backIV.setOnClickListener { findNavController().navigateUp() }
        modifyPasswordViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(ModifyPasswordViewModel::class.java)
        val username = requireActivity().getSharedPreferences(USER, Context.MODE_PRIVATE).getString(USERNAME, "")!!
        accountTV.text = username

        modifyPassword.setOnClickListener {
            val oldPassword = oldPasswordET.text.toString()
            val newPassword = newPasswordET.text.toString()
            val confirmPassword = confirmPasswordET.text.toString()
            if (newPassword != confirmPassword) {
                Toast.makeText(requireContext(), "两次输入密码不一致！", Toast.LENGTH_SHORT).show()
            } else if (newPassword.isEmpty() || confirmPassword.isEmpty() || oldPassword.isEmpty()) {
                Toast.makeText(requireContext(), "请输入数据！", Toast.LENGTH_SHORT).show()
            } else {
                modifyPasswordViewModel.modifyPassword(username, oldPassword, newPassword)
            }
        }
        modifyPasswordViewModel.passwordStatus.observe(viewLifecycleOwner, {
            when (it) {
                //modifyPasswordViewModel.resetPasswordStatus()不能放在when外面，否则就会形成一个死循环
                PASSWORD_SUCCESS -> {
                    Toast.makeText(requireContext(), "修改成功", Toast.LENGTH_SHORT).show()
                    modifyPasswordViewModel.resetPasswordStatus()
                    findNavController().navigateUp()
                }
                PASSWORD_FAILURE -> {
                    Toast.makeText(requireContext(), "修改失败！密码错误！", Toast.LENGTH_SHORT).show()
                    modifyPasswordViewModel.resetPasswordStatus()
                }
                REQUEST_ERROR -> {
                    Toast.makeText(requireContext(), "网络错误！请稍后再试", Toast.LENGTH_SHORT).show()
                    modifyPasswordViewModel.resetPasswordStatus()
                }
            }
        })
    }
}