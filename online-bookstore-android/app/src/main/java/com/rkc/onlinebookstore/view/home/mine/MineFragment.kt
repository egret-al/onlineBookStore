package com.rkc.onlinebookstore.view.home.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.viewmodel.home.mine.MineViewModel
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : Fragment() {
    private lateinit var mineViewModel: MineViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mineViewModel = ViewModelProvider(this).get(MineViewModel::class.java)
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //我的信息
        mineInformation.setOnClickListener {  }
        //订单
        mineOrder.setOnClickListener {
            findNavController().navigate(R.id.action_nav_mine_to_orderListFragment, null)
        }
        //退出
        exit.setOnClickListener {  }
    }
}