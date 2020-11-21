package com.rkc.onlinebookstore.view.home.mine

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.viewmodel.login.NICKNAME
import com.rkc.onlinebookstore.viewmodel.login.USER
import com.rkc.onlinebookstore.viewmodel.login.USERNAME
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        load()
        //我的信息
        mineInformation.setOnClickListener { findNavController().navigate(R.id.action_nav_mine_to_mineInfoFragment) }
        //订单
        mineOrder.setOnClickListener { findNavController().navigate(R.id.action_nav_mine_to_orderListFragment, null) }
        //收货地址
        receivingAddress.setOnClickListener { findNavController().navigate(R.id.action_nav_mine_to_addressFragment, null) }
        //编辑资料
//        editInfo.setOnClickListener { findNavController().navigate(R.id.action_mineInfoFragment_to_editInfoFragment) }
    }

    private fun load() {
        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1605784182040&di=ffd7c359f16e02a55265319741d57f6c&imgtype=0&src=http%3A%2F%2Fblog.gqylpy.com%2Fmedia%2Fai%2F2019-09%2F9a6d0d9d-1477-453d-843f-f3b7442390cb.png")
            .placeholder(R.drawable.book_placeholder)
            .into(imgHeader)
        val sharedPreferences = requireActivity().application.getSharedPreferences(USER, Context.MODE_PRIVATE)
        account.text = sharedPreferences.getString(USERNAME, "")
        nickname.text = sharedPreferences.getString(NICKNAME, "")
    }
}