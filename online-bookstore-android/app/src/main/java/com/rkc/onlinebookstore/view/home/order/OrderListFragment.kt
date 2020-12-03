package com.rkc.onlinebookstore.view.home.order

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            setItemViewCacheSize(-1)
            adapter = orderListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        orderListViewModel.ackFailure.observe(viewLifecycleOwner, {
            if (it > 0) {
                Toast.makeText(requireContext(), "签收失败！", Toast.LENGTH_SHORT).show()
            }
        })
        orderListViewModel.ackSuccessPosition.observe(viewLifecycleOwner, {
            if (it > -1) {
                Toast.makeText(requireContext(), "签收成功！", Toast.LENGTH_SHORT).show()
                orderListAdapter.notifyItemChanged(it)
            }
        })
    }
}