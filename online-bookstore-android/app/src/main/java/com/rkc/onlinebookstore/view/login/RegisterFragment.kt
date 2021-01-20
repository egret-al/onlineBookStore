package com.rkc.onlinebookstore.view.login

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.model.user.Account
import com.rkc.onlinebookstore.model.user.User
import com.rkc.onlinebookstore.util.VerifyUtils
import com.rkc.onlinebookstore.viewmodel.login.RegisterViewModel
import kotlinx.android.synthetic.main.address_item.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.nicknameET
import java.util.*

class RegisterFragment : Fragment() {
    private var sexSelected = false
    private var currentSex = "男"
    private lateinit var birthdayStr: String

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        registerViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(RegisterViewModel::class.java)
        btnToLogin.setOnClickListener { NavHostFragment.findNavController(this).navigateUp() }

        btnDatePickerDialog.setOnClickListener {
            val calendar = Calendar.getInstance()
            val mYear = calendar[Calendar.YEAR]
            val mMonth = calendar[Calendar.MONTH] + 1
            val mDay = calendar[Calendar.DAY_OF_MONTH]
            DatePickerDialog(requireContext(), { datePicker, year, month, dayOfMonth ->
                run {
                    val sYear = year
                    val sMonth = leadingZero(month)
                    val sDay = leadingZero(dayOfMonth)
                    birthdayStr = "${sYear}-${sMonth}-${sDay}"
                    birthday.text = birthdayStr
                }
            }, mYear, mMonth, mDay).show()
        }

        sexRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            run {
                when (checkedId) {
                    maleRadioButton.id -> currentSex = maleRadioButton.text.toString()
                    femaleRadioButton.id -> currentSex = femaleRadioButton.text.toString()
                }
                sexSelected = true
            }
        }

        btnSubmitRegister.setOnClickListener {
            if (usernameET.text.trim().length != 10) {
                Toast.makeText(requireContext(), "账号必须是10位！", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (passwordET.text.trim().length < 6 || passwordET.text.trim().length > 10) {
                Toast.makeText(requireContext(), "密码必须是6到10位！", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (nicknameET.text.isEmpty()) {
                Toast.makeText(requireContext(), "昵称不能为空！", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!VerifyUtils.isPhone(phoneET.text.toString())) {
                Toast.makeText(requireContext(), "手机号码格式错误！", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!sexSelected) {
                Toast.makeText(requireContext(), "请选择性别！", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (birthday.text.isEmpty()) {
                Toast.makeText(requireContext(), "请选择生日！", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (passwordET.text.trim().toString().equals(confirmPasswordET.text.trim().toString())) {
                val account = Account().apply {
                    username = usernameET.text.trim().toString()
                    password = passwordET.text.trim().toString()
                }
                val user = User().apply {
                    nickname = nicknameET.text.trim().toString()
                    sex = currentSex
                    phone = phoneET.text.trim().toString()
                }
                //kotlin的日期要进行特殊处理才能被后端解析，为了简单起见，直接使用字符串
                registerViewModel.register(account, user, birthdayStr)
            } else {
                Toast.makeText(requireContext(), "两次输入密码不一致！", Toast.LENGTH_SHORT).show()
            }
        }

        registerViewModel.registerFailure.observe(viewLifecycleOwner, {
            if (it > 0) Toast.makeText(requireContext(), registerViewModel.resultMessage.value, Toast.LENGTH_SHORT).show()
        })
        registerViewModel.registerSuccess.observe(viewLifecycleOwner, {
            if (it > 0) {
                Toast.makeText(requireContext(), registerViewModel.resultMessage.value, Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        })
    }

    private fun leadingZero(num: Int): String {
        if (num in 1..9) {
            return "0${num}"
        }
        return "$num"
    }
}