package com.rkc.onlinebookstore.adapter.order

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.model.order.ORDER_EXPIRE
import com.rkc.onlinebookstore.model.order.ORDER_FINISHED
import com.rkc.onlinebookstore.model.order.ORDER_UN_PAYMENT
import com.rkc.onlinebookstore.viewmodel.home.order.OrderListViewModel
import kotlinx.android.synthetic.main.order_list_item.view.*

/**
 * @author rkc
 * @date 2020/11/15 20:55
 * @version 1.0
 */
const val ORDER_KEY = "order_key"

class OrderListAdapter(private val orderListViewModel: OrderListViewModel) : RecyclerView.Adapter<OrderListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        val holder = OrderListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.order_list_item, parent, false))
        holder.itemView.orderItemViewDetails.setOnClickListener {
            val order = orderListViewModel.orderList.value?.get(holder.absoluteAdapterPosition)
            //进入详情页面
            Bundle().apply {
                putParcelable(ORDER_KEY, order)
                holder.itemView.findNavController().navigate(R.id.action_orderListFragment_to_orderDetailFragment, this)
            }
        }
        holder.itemView.orderItemDelete.setOnClickListener {
            val order = orderListViewModel.orderList.value?.get(holder.absoluteAdapterPosition)
            //调用viewModel进行删除
//            Log.d("tag", holder.absoluteAdapterPosition.toString())
            order?.serialNumber?.let { it1 -> orderListViewModel.deleteOrder(it1, holder.absoluteAdapterPosition) }
        }
        return holder
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        val order = orderListViewModel.orderList.value?.get(position)
        with(holder.itemView) {
            orderItemSerial.text = order?.serialNumber
            orderItemProductCount.text = order?.productCount.toString()
            orderItemContent.text = order?.orderContent
            orderItemCreateTime.text = order?.createTime.toString()

            when (order?.orderPaymentStatus) {
                ORDER_FINISHED -> {
                    orderItemTotal.text = order.wholePrice.toString()
                    orderItemStatus.text = "已完成"
                    orderItemStatus.setTextColor(Color.GREEN)
                }
                ORDER_UN_PAYMENT -> {
                    orderItemTotal.text = "--"
                    orderItemStatus.text = "未支付"
                    orderItemStatus.setTextColor(Color.RED)
                }
                ORDER_EXPIRE -> {
                    orderItemTotal.text = "--"
                    orderItemStatus.text = "已失效"
                    orderItemStatus.setTextColor(Color.DKGRAY)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return orderListViewModel.orderList.value!!.size
    }
}

class OrderListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)