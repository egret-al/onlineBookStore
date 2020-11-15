package com.rkc.onlinebookstore.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.model.order.ORDER_EXPIRE
import com.rkc.onlinebookstore.model.order.ORDER_FINISHED
import com.rkc.onlinebookstore.model.order.ORDER_UN_PAYMENT
import com.rkc.onlinebookstore.model.order.Order
import kotlinx.android.synthetic.main.order_list_item.view.*

/**
 * @author rkc
 * @date 2020/11/15 20:55
 * @version 1.0
 */
class OrderListAdapter : ListAdapter<Order, OrderListViewHolder>(DiffCallBack) {

    object DiffCallBack : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.serialNumber == newItem.serialNumber
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        val holder = OrderListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.order_list_item, parent, false))
        holder.itemView.orderItemViewDetails.setOnClickListener {
            val order = getItem(holder.absoluteAdapterPosition)
            //进入详情页面
        }
        holder.itemView.orderItemDelete.setOnClickListener {
            val order = getItem(holder.absoluteAdapterPosition)
            //直接删除
        }
        return holder
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        val order = getItem(position) ?: return
        with(holder.itemView) {
            orderItemSerial.text = order.serialNumber
            orderItemProductCount.text = order.productCount.toString()
            orderItemContent.text = order.orderContent
            orderItemCreateTime.text = order.createTime.toString()

            when (order.orderPaymentStatus) {
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

}

class OrderListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)