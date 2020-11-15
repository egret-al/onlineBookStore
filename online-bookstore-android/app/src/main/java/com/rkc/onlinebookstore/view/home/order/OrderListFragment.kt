package com.rkc.onlinebookstore.view.home.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.adapter.OrderListAdapter
import com.rkc.onlinebookstore.adapter.OrderListViewHolder
import com.rkc.onlinebookstore.model.order.Order
import com.rkc.onlinebookstore.viewmodel.home.order.OrderListViewModel
import kotlinx.android.synthetic.main.fragment_order_list.*

class OrderListFragment : Fragment() {
    private lateinit var orderListViewModel: OrderListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        orderListViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(OrderListViewModel::class.java)
        orderListViewModel.orderList.observe(viewLifecycleOwner, { refreshRecyclerView(it) })
        orderListViewModel.fetchOrderList()
    }

    private fun refreshRecyclerView(orderList: List<Order>) {
        val orderListAdapter = OrderListAdapter().apply { submitList(orderList) }
        with(orderListRV) {
            adapter = orderListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}