package com.rkc.onlinebookstore.view.home.mine.address

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.adapter.address.AddressListAdapter
import com.rkc.onlinebookstore.viewmodel.home.mine.address.AddressViewModel
import com.rkc.onlinebookstore.viewmodel.login.DEFAULT_ADDRESS
import com.rkc.onlinebookstore.viewmodel.login.USER
import kotlinx.android.synthetic.main.common_title.*
import kotlinx.android.synthetic.main.fragment_address.*

class AddressFragment : Fragment() {
    private lateinit var addressViewModel: AddressViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_address, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        titleTV.text = "我的收货地址"
        backIV.setOnClickListener { findNavController().navigateUp() }
        addressViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(
            AddressViewModel::class.java)
        //初始的默认值，不一定一直是默认值
        var defaultId = requireActivity().application.getSharedPreferences(USER, Context.MODE_PRIVATE).getInt(DEFAULT_ADDRESS, -1)
        addressViewModel.addressesLiveData.observe(viewLifecycleOwner, {
            addressesRV.adapter = AddressListAdapter(defaultId, addressViewModel).apply { submitList(it) }
            addressesRV.layoutManager = LinearLayoutManager(requireContext())
        })
        addressViewModel.setDefaultStatus.observe(viewLifecycleOwner, {
            if (it != 0) {
                defaultId = requireActivity().application.getSharedPreferences(USER, Context.MODE_PRIVATE).getInt(DEFAULT_ADDRESS, -1)
                Toast.makeText(requireContext(), "设置成功！", Toast.LENGTH_SHORT).show()
                //刷新界面
                addressViewModel.fetchAddresses()
            }
        })

        addButton.setOnClickListener {
            Bundle().apply {
                putInt(OPERATION_TYPE, OPERATION_ADD)
                findNavController().navigate(R.id.action_addressFragment_to_addressEditFragment, this)
            }
        }
        addressViewModel.fetchAddresses()
    }
}