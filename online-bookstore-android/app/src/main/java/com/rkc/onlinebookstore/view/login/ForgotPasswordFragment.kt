package com.rkc.onlinebookstore.view.login

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.room.util.StringUtil
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.model.user.Account
import com.rkc.onlinebookstore.viewmodel.login.ForgotPasswordViewModel
import kotlinx.android.synthetic.main.fragment_forgot_password.*

class ForgotPasswordFragment : Fragment() {
    private lateinit var forgotPasswordViewModel: ForgotPasswordViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        forgotPasswordViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(ForgotPasswordViewModel::class.java)
        resetPasswordBtn.setOnClickListener {
            if (verifyData()) {
                //重置密码请求
                val account = Account().apply {
                    username = accountET.text.toString()
                    password = newPasswordET.text.toString()
                }
                forgotPasswordViewModel.resetPassword(account, smsCodeET.text.toString())
            } else {
                Toast.makeText(requireContext(), "请填写正确的数据！", Toast.LENGTH_SHORT).show()
            }
        }
        sendCodeBtn.setOnClickListener {
            //发送验证码请求
            if (accountET.text.isEmpty() || phoneNumberET.text.isEmpty()) {
                Toast.makeText(requireContext(), "数据不全！", Toast.LENGTH_SHORT).show()
            } else {
                object : CountDownTimer(60000, 1000) {
                    @SuppressLint("SetTextI18n")
                    override fun onTick(millisUntilFinished: Long) {
                        if (sendCodeBtn != null) {
                            sendCodeBtn.apply {
                                isClickable = false
                                isEnabled = false
                                text = "${millisUntilFinished / 1000}s"
                                setBackgroundColor(Color.parseColor("#8F8F8F"))
                            }
                        }
                    }

                    override fun onFinish() {
                        if (sendCodeBtn != null) {
                            sendCodeBtn.apply {
                                text = "重新获取"
                                setBackgroundColor(Color.parseColor("#26A2FF"))
                                isClickable = true
                                isEnabled = true
                            }
                        }
                    }
                }.start()
                val account = Account().apply { username = accountET.text.toString() }
                forgotPasswordViewModel.sendCode(account, phoneNumberET.text.toString())
            }
        }

        forgotPasswordViewModel.resetSuccess.observe(viewLifecycleOwner, {
            if (it > 0) {
                Toast.makeText(requireContext(), "重置成功！", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        })
        forgotPasswordViewModel.resetFailure.observe(viewLifecycleOwner, {
            if (it > 0) Toast.makeText(requireContext(), "修改失败！信息错误", Toast.LENGTH_SHORT).show()
        })
        forgotPasswordViewModel.sendCodeSuccess.observe(viewLifecycleOwner, {
            if (it > 0) Toast.makeText(requireContext(), "发送成功！请查收您的短信", Toast.LENGTH_SHORT).show()
        })
        forgotPasswordViewModel.sendCodeFailure.observe(viewLifecycleOwner, {
            if (it > 0) Toast.makeText(requireContext(), "请不要多次发送！", Toast.LENGTH_SHORT).show()
        })
    }

    private fun verifyData(): Boolean {
        if (accountET.text.isEmpty() || newPasswordET.text.isEmpty() || confirmPasswordET.text.isEmpty() || smsCodeET.text.isEmpty()) return false
        if (newPasswordET.text.toString() != confirmPasswordET.text.toString()) return false
        return true;
    }
}