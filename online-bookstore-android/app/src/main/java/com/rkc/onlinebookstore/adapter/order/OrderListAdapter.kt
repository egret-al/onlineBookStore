package com.rkc.onlinebookstore.adapter.order

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.model.order.*
import com.rkc.onlinebookstore.view.home.home.DATE_FORMAT
import com.rkc.onlinebookstore.viewmodel.home.order.OrderListViewModel
import kotlinx.android.synthetic.main.order_list_item.view.*
import java.text.SimpleDateFormat

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
            //进入详情页面
            Bundle().apply {
                val order = orderListViewModel.orderList.value?.get(holder.absoluteAdapterPosition)
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

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        val order = orderListViewModel.orderList.value!![position]
        with(holder.itemView) {
            orderItemSerial.text = order.serialNumber
            orderItemProductCount.text = order.productCount.toString()
            orderItemContent.text = order.orderContent
            orderItemCreateTime.text = SimpleDateFormat(DATE_FORMAT).format(order.createTime)

            when (order.orderPaymentStatus) {
                ORDER_FINISHED -> {
                    orderItemTotal.text = order.wholePrice.toString()
                    orderItemStatus.text = "已支付"
                    orderItemStatus.setTextColor(Color.GREEN)
                    when (order.sendStatus) {
                        WAIT_SEND -> {
                            ackStatusTV.text = "待发货"
                            ackStatusTV.visibility = View.VISIBLE
                            ackStatusTV.setTextColor(resources.getColor(R.color.blue))
                        }
                        ACKNOWLEDGED -> {
                            ackStatusTV.text = "已签收"
                            ackStatusTV.visibility = View.VISIBLE
                            ackStatusTV.setTextColor(resources.getColor(R.color.deep_gray))
                        }
                        UN_ACKNOWLEDGE -> {
                            ackStatusTV.text = "待签收"
                            ackStatusTV.setTextColor(resources.getColor(R.color.green))
                            ackStatusTV.visibility = View.VISIBLE
                            ackStatusTV.setOnClickListener { orderListViewModel.tryAcknowledge(order.serialNumber) }
                        }
                    }
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