package com.rkc.onlinebookstore.adapter.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.adapter.home.BOOK_BUNDLE_KEY
import com.rkc.onlinebookstore.model.user.ShoppingTrolley
import com.rkc.onlinebookstore.viewmodel.home.shopping.ShoppingTrolleyViewModel
import kotlinx.android.synthetic.main.shopping_trolley_item.view.*

/**
 * @author rkc
 * @date 2020/11/26 10:00
 * @version 1.0
 */
class ShoppingTrolleysAdapter(private val shoppingTrolleyViewModel: ShoppingTrolleyViewModel)
    : ListAdapter<ShoppingTrolley, ShoppingTrolleysViewHolder>(DiffCallBack) {

    object DiffCallBack : DiffUtil.ItemCallback<ShoppingTrolley>() {
        override fun areItemsTheSame(oldItem: ShoppingTrolley, newItem: ShoppingTrolley): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ShoppingTrolley, newItem: ShoppingTrolley): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingTrolleysViewHolder {
        val holder = ShoppingTrolleysViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.shopping_trolley_item, parent, false))
        //查看详情
        holder.itemView.shoppingDetailTV.setOnClickListener {
            val book = getItem(holder.absoluteAdapterPosition).book
            Bundle().apply {
                putParcelable(BOOK_BUNDLE_KEY, book)
                holder.itemView.findNavController().navigate(R.id.action_nav_shopping_to_bookDetailFragment, this)
            }
        }
        //删除
        holder.itemView.shoppingDeleteTV.setOnClickListener { shoppingTrolleyViewModel.deleteShoppingTrolleyById(getItem(holder.absoluteAdapterPosition).id) }
        return holder
    }

    override fun onBindViewHolder(holder: ShoppingTrolleysViewHolder, position: Int) {
        val shoppingTrolley = getItem(position) ?: return
        Glide.with(holder.itemView).load(shoppingTrolley.book.mainCover)
            .placeholder(R.drawable.book_placeholder)
            .into(holder.itemView.bookMainCoverIV)
        with(holder.itemView) {
            bookNameTV.text = shoppingTrolley.book.bookName
            collectCountTV.text = shoppingTrolley.collectCount.toString()
            collectTimeTV.text = shoppingTrolley.createTime.toString()
        }
    }
}

class ShoppingTrolleysViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)