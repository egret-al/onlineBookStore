package com.rkc.onlinebookstore.view.home.shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.adapter.shopping.ShoppingTrolleysAdapter
import com.rkc.onlinebookstore.viewmodel.home.shopping.ShoppingTrolleyViewModel
import kotlinx.android.synthetic.main.fragment_shopping_trolley.*

class ShoppingTrolleyFragment : Fragment() {
    private lateinit var shoppingTrolleyViewModel: ShoppingTrolleyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_shopping_trolley, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        shoppingTrolleyViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(ShoppingTrolleyViewModel::class.java)
        shoppingTrolleysRV.layoutManager = LinearLayoutManager(requireContext())
        shoppingTrolleyViewModel.shoppingTrolleyLiveData.observe(viewLifecycleOwner, {
            if (it.size != 0) {
                remindTV.visibility = View.GONE
                shoppingTrolleysRV.adapter = ShoppingTrolleysAdapter(shoppingTrolleyViewModel).apply { submitList(it) }
            } else {
                remindTV.visibility = View.VISIBLE
                shoppingTrolleysRV.adapter = null
            }
        })
        shoppingTrolleyViewModel.deleteStatus.observe(viewLifecycleOwner, {
            if (it != 0) {
                Toast.makeText(requireContext(), "删除成功！", Toast.LENGTH_SHORT).show()
                shoppingTrolleyViewModel.fetchShoppingTrolleys()
            }
        })
        shoppingTrolleyViewModel.fetchShoppingTrolleys()
    }
}