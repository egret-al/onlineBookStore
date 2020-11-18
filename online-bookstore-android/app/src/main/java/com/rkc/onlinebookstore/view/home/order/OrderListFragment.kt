package com.rkc.onlinebookstore.view.home.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.adapter.order.OrderListAdapter
import com.rkc.onlinebookstore.viewmodel.home.order.OrderListViewModel
import kotlinx.android.synthetic.main.fragment_order_list.*

class OrderListFragment : Fragment() {
    private lateinit var orderListViewModel: OrderListViewModel
    private lateinit var orderListAdapter: OrderListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        orderListViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(OrderListViewModel::class.java)
        orderListAdapter = OrderListAdapter(orderListViewModel)

        orderListViewModel.orderList.observe(viewLifecycleOwner, { orderListAdapter.notifyDataSetChanged() })
        orderListViewModel.orderDelete.observe(viewLifecycleOwner, { orderListAdapter.notifyDataSetChanged() })
        orderListViewModel.fetchOrderList()

        with(orderListRV) {
            adapter = orderListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}