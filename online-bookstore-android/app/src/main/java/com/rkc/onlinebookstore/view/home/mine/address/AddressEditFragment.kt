package com.rkc.onlinebookstore.view.home.mine.address

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.adapter.address.ADDRESS_BUNDLE_KEY
import com.rkc.onlinebookstore.model.user.Address
import com.rkc.onlinebookstore.util.VerifyUtils
import com.rkc.onlinebookstore.viewmodel.home.mine.address.AddressEditViewModel
import com.rkc.onlinebookstore.viewmodel.home.mine.address.ERROR
import com.rkc.onlinebookstore.viewmodel.home.mine.address.SUCCESS
import kotlinx.android.synthetic.main.fragment_address_edit.*

const val OPERATION_EDIT = 1
const val OPERATION_ADD = 2
const val OPERATION_TYPE = "operation_type"

class AddressEditFragment : Fragment() {
    private lateinit var addressEditViewModel: AddressEditViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_address_edit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addressEditViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(
            AddressEditViewModel::class.java)
        //编辑模式
        if (arguments?.getInt(OPERATION_TYPE) == OPERATION_EDIT) {
            val address = arguments?.getParcelable<Address>(ADDRESS_BUNDLE_KEY)!!
            loadEditMode(address, address.id)
        } else if (arguments?.getInt(OPERATION_TYPE) == OPERATION_ADD) {
            //增加模式
            loadAddMode()
        }
    }

    private fun loadAddMode() {
        addressEditViewModel.addStatus.observe(viewLifecycleOwner, {
            when (it) {
                ERROR -> { Toast.makeText(requireContext(), "添加失败！请稍后重试！", Toast.LENGTH_SHORT).show() }
                SUCCESS -> {
                    Toast.makeText(requireContext(), "添加成功", Toast.LENGTH_SHORT).show()
                    reset()
                }
            }
        })
        saveButton.setOnClickListener {
            val address = Address().apply {
                phone = phoneNumberET.text.toString()
                receiverName = receiverNameET.text.toString()
                address = detailAddressET.text.toString()
            }
            if (validData(address)) {
                addressEditViewModel.add(address)
            } else {
                Toast.makeText(requireContext(), "数据不规范！", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadEditMode(addr: Address, id: Int) {
        receiverNameET.setText(addr.receiverName)
        phoneNumberET.setText(addr.phone)
        detailAddressET.setText(addr.address)
        addressEditViewModel.saveStatus.observe(viewLifecycleOwner, {
            when (it) {
                ERROR -> { Toast.makeText(requireContext(), "保存失败！请稍后重试！", Toast.LENGTH_SHORT).show() }
                SUCCESS -> {
                    Toast.makeText(requireContext(), "添加成功", Toast.LENGTH_SHORT).show()
                    reset()
                }
            }
        })
        //调用ViewModel发起请求服务器进行修改
        saveButton.setOnClickListener {
            val address = Address().apply {
                phone = phoneNumberET.text.toString()
                receiverName = receiverNameET.text.toString()
                address = detailAddressET.text.toString()
                this.id = id
            }
            if (validData(address)) {
                addressEditViewModel.save(address)
            } else {
                Toast.makeText(requireContext(), "数据不规范！", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validData(address: Address): Boolean {
        if (address.address.length == 1 || address.phone.length != 11 || address.receiverName.isEmpty()) return false
        if (!VerifyUtils.isPhone(address.phone)) return false
        return true
    }

    private fun reset() {
        receiverNameET.setText("")
        phoneNumberET.setText("")
        detailAddressET.setText("")
    }
}