package com.rkc.onlinebookstore.view.home.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.viewmodel.home.mine.MineViewModel

class MineFragment : Fragment() {

    private lateinit var mineViewModel: MineViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mineViewModel = ViewModelProvider(this).get(MineViewModel::class.java)
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }
}