package com.rkc.onlinebookstore.view.home.mine.info

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.model.user.Account
import com.rkc.onlinebookstore.viewmodel.home.mine.info.ModifyNicknameViewModel
import com.rkc.onlinebookstore.viewmodel.login.NICKNAME
import com.rkc.onlinebookstore.viewmodel.login.USER
import kotlinx.android.synthetic.main.common_title.*
import kotlinx.android.synthetic.main.fragment_modify_nickname.*

class ModifyNicknameFragment : Fragment() {
    private lateinit var modifyNicknameViewModel: ModifyNicknameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_modify_nickname, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        titleTV.text = "修改昵称"
        backIV.setOnClickListener { findNavController().navigateUp() }
        modifyNicknameViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(ModifyNicknameViewModel::class.java)
        val account = arguments?.getParcelable<Account>(ACCOUNT_BUNDLE_KEY)!!
        nicknameET.setText(account.user.nickname)
        modifyNicknameViewModel.nicknameStatus.observe(viewLifecycleOwner, {
            if (it > 0) {
                Toast.makeText(requireContext(), "修改成功", Toast.LENGTH_SHORT).show()
                requireActivity().application.getSharedPreferences(USER, Context.MODE_PRIVATE).edit().apply {
                    putString(NICKNAME, nicknameET.text.toString())
                    apply()
                }
                findNavController().navigateUp()
            }
        })
        saveNickname.setOnClickListener {
            val newNickname = nicknameET.text.toString()
            if (newNickname.isEmpty()) {
                Toast.makeText(requireContext(), "请输入昵称", Toast.LENGTH_SHORT).show()
            } else {
                val user = account.user.apply { nickname = newNickname }
                modifyNicknameViewModel.saveNickname(user)
            }
        }
    }
}