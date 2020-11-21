package com.rkc.onlinebookstore.view.home.mine.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.model.user.Account
import com.rkc.onlinebookstore.viewmodel.home.mine.info.ModifyPhoneViewModel
import kotlinx.android.synthetic.main.fragment_modify_phone.*

class ModifyPhoneFragment : Fragment() {
    private lateinit var modifyPhoneViewModel: ModifyPhoneViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_modify_phone, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        modifyPhoneViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(ModifyPhoneViewModel::class.java)
        val account = arguments?.getParcelable<Account>(ACCOUNT_BUNDLE_KEY)!!
        phoneET.setText(account.user.phone)
        modifyPhoneViewModel.phoneStatus.observe(viewLifecycleOwner, {
            if (it > 0) {
                Toast.makeText(requireContext(), "修改成功", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        })
        savePhone.setOnClickListener {
            val phone = phoneET.text.toString()
            if (phone.isEmpty()) {
                Toast.makeText(requireContext(), "请输入手机号码", Toast.LENGTH_SHORT).show()
            } else {
                val user = account.user.apply { this.phone = phone }
                modifyPhoneViewModel.savePhone(user)
            }
        }
    }
}