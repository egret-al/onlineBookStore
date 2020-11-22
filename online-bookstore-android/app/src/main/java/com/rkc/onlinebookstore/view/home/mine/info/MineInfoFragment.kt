package com.rkc.onlinebookstore.view.home.mine.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.viewmodel.home.mine.info.FEMALE
import com.rkc.onlinebookstore.viewmodel.home.mine.info.MALE
import com.rkc.onlinebookstore.viewmodel.home.mine.info.MineInfoViewModel
import kotlinx.android.synthetic.main.fragment_mine.imgHeader
import kotlinx.android.synthetic.main.fragment_mine_info.*

const val ACCOUNT_BUNDLE_KEY = "account_key"

class MineInfoFragment : Fragment() {
    private var isFirst = true
    private lateinit var mineInfoViewModel: MineInfoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mine_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mineInfoViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(
            MineInfoViewModel::class.java)
        load()
        mineInfoViewModel.fetchUserAndAccount()
        //修改密码
        updatePassword.setOnClickListener { findNavController().navigate(R.id.action_mineInfoFragment_to_modifyPasswordFragment, null) }

        sexRG.setOnCheckedChangeListener { group, checkedId ->
            run {
                if (!isFirst) {
                    when (checkedId) {
                        maleRB.id -> mineInfoViewModel.modifySex(MALE)
                        femaleRB.id -> mineInfoViewModel.modifySex(FEMALE)
                    }
                } else {
                    isFirst = false
                }
            }
        }
        //修改昵称
        modifyNickname.setOnClickListener {
            Bundle().apply {
                putParcelable(ACCOUNT_BUNDLE_KEY, mineInfoViewModel.accountLiveData.value)
                findNavController().navigate(R.id.action_mineInfoFragment_to_modifyNicknameFragment, this)
            }
        }
        //修改手机
        modifyPhone.setOnClickListener {
            Bundle().apply {
                putParcelable(ACCOUNT_BUNDLE_KEY, mineInfoViewModel.accountLiveData.value)
                findNavController().navigate(R.id.action_mineInfoFragment_to_modifyPhoneFragment, this)
            }
        }
        //余额充值
        addResidue.setOnClickListener {
            Bundle().apply {
                putParcelable(ACCOUNT_BUNDLE_KEY, mineInfoViewModel.accountLiveData.value)
                findNavController().navigate(R.id.action_mineInfoFragment_to_topUpFragment, this)
            }
        }
    }

    private fun load() {
        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1605784182040&di=ffd7c359f16e02a55265319741d57f6c&imgtype=0&src=http%3A%2F%2Fblog.gqylpy.com%2Fmedia%2Fai%2F2019-09%2F9a6d0d9d-1477-453d-843f-f3b7442390cb.png")
            .placeholder(R.drawable.book_placeholder)
            .into(imgHeader)
        mineInfoViewModel.accountLiveData.observe(viewLifecycleOwner, {
            accountTV.text = it.username
            nicknameTV.text = it.user.nickname
            phoneTV.text = it.user.phone
            scoreTV.text = it.score.toString()
            residueTV.text = it.balance.toString()
            if (it.user.sex == "男") maleRB.isChecked = true
            else femaleRB.isChecked = true
        })
    }
}