package com.rkc.onlinebookstore.view.home.mine.info

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
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.model.user.Account
import com.rkc.onlinebookstore.util.VerifyUtils
import com.rkc.onlinebookstore.viewmodel.home.mine.info.ModifyPhoneViewModel
import kotlinx.android.synthetic.main.common_title.*
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import kotlinx.android.synthetic.main.fragment_modify_phone.*
import kotlinx.android.synthetic.main.fragment_modify_phone.sendCodeBtn

class ModifyPhoneFragment : Fragment() {
    private lateinit var modifyPhoneViewModel: ModifyPhoneViewModel
    private var isSend = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_modify_phone, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        titleTV.text = "修改手机号码"
        backIV.setOnClickListener { findNavController().navigateUp() }
        modifyPhoneViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(ModifyPhoneViewModel::class.java)
        val account = arguments?.getParcelable<Account>(ACCOUNT_BUNDLE_KEY)!!
        oldPasswordET.text = account.user.phone
        modifyPhoneViewModel.phoneSuccess.observe(viewLifecycleOwner, {
            if (it > 0) {
                Toast.makeText(requireContext(), modifyPhoneViewModel.phoneResultMessage.value, Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        })
        modifyPhoneViewModel.phoneFailure.observe(viewLifecycleOwner, {
            if (it > 0) Toast.makeText(requireContext(), modifyPhoneViewModel.phoneResultMessage.value, Toast.LENGTH_SHORT).show()
        })
        modifyPhoneViewModel.sendFailure.observe(viewLifecycleOwner, {
            if (it > 0) Toast.makeText(requireContext(), modifyPhoneViewModel.sendResultMessage.value, Toast.LENGTH_SHORT).show()
        })
        modifyPhoneViewModel.sendSuccess.observe(viewLifecycleOwner, {
            if (it > 0) {
                Toast.makeText(requireContext(), modifyPhoneViewModel.sendResultMessage.value, Toast.LENGTH_SHORT).show()
                //倒计时
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
                                text = "发送"
                                setBackgroundColor(Color.parseColor("#26A2FF"))
                                isClickable = true
                                isEnabled = true
                            }
                        }
                    }
                }.start()
                isSend = true
            }
        })
        //发送验证码
        sendCodeBtn.setOnClickListener { modifyPhoneViewModel.sendCode(account.user) }
        //修改手机号码
        savePhone.setOnClickListener {
            if (isSend) {
                val phone = phoneET.text.toString()
                val code = verifyCodeET.text.toString()
                if (phone.isEmpty() || code.isEmpty()) {
                    Toast.makeText(requireContext(), "请输入手机号码和验证码！", Toast.LENGTH_SHORT).show()
                } else {
                    if (!VerifyUtils.isPhone(phone)) {
                        Toast.makeText(requireContext(), "错误的手机号码！", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    val user = account.user.apply { this.phone = phone }
                    modifyPhoneViewModel.savePhone(user, code)
                }
            } else {
                Toast.makeText(requireContext(), "请先发送验证码！", Toast.LENGTH_SHORT).show()
            }
        }
    }
}