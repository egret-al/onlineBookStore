package com.rkc.onlinebookstore.view.home.mine.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.model.user.Account
import com.rkc.onlinebookstore.viewmodel.home.mine.info.ADD_FAILURE
import com.rkc.onlinebookstore.viewmodel.home.mine.info.ADD_SUCCESS
import com.rkc.onlinebookstore.viewmodel.home.mine.info.REQUEST_STATUS
import com.rkc.onlinebookstore.viewmodel.home.mine.info.TopUpViewModel
import kotlinx.android.synthetic.main.fragment_top_up.*

class TopUpFragment : Fragment() {
    private lateinit var topUpViewModel: TopUpViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_top_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        topUpViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(TopUpViewModel::class.java)
        val account = arguments?.getParcelable<Account>(ACCOUNT_BUNDLE_KEY)!!
        accountToppedUpTV.text = account.username
        topUpViewModel.initBalance(account.balance)
        topUpViewModel.balanceLiveData.observe(viewLifecycleOwner, { accountResidueTV.text = it.toString() })

        topUpBtn.setOnClickListener {
            val count = alternativeSpinner.selectedItem.toString().toInt()
            topUpViewModel.doTopUpResidue(account.username, count)
        }

        topUpViewModel.addStatus.observe(viewLifecycleOwner, {
            when (it) {
                ADD_SUCCESS -> {
                    Toast.makeText(requireContext(), "充值成功！", Toast.LENGTH_SHORT).show()
                    topUpViewModel.resetStatus()
                }
                ADD_FAILURE -> {
                    Toast.makeText(requireContext(), "充值失败！", Toast.LENGTH_SHORT).show()
                    topUpViewModel.resetStatus()
                }
                REQUEST_STATUS -> {
                    Toast.makeText(requireContext(), "网络错误！", Toast.LENGTH_SHORT).show()
                    topUpViewModel.resetStatus()
                }
            }
        })
    }
}