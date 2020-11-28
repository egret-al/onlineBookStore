package com.rkc.onlinebookstore.adapter.home

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
import com.rkc.onlinebookstore.model.book.BookType
import kotlinx.android.synthetic.main.book_type_item.view.*

/**
 * @author rkc
 * @date 2020/11/28 8:48
 * @version 1.0
 */
const val BOOK_TYPE_ID = "book_type_id"

class BookTypeListAdapter : ListAdapter<BookType, BookTypeListViewHolder>(DiffCallBack) {

    object DiffCallBack : DiffUtil.ItemCallback<BookType>() {
        override fun areItemsTheSame(oldItem: BookType, newItem: BookType): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: BookType, newItem: BookType): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookTypeListViewHolder {
        val holder = BookTypeListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.book_type_item, parent, false))
        //添加点击事件，点击对应的商品类型，进入对应的类型页面
        holder.itemView.setOnClickListener {
            Bundle().apply {
                putInt(BOOK_TYPE_ID, getItem(holder.absoluteAdapterPosition).id)
                holder.itemView.findNavController().navigate(R.id.action_nav_home_to_bookTypeHomeFragment, this)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: BookTypeListViewHolder, position: Int) {
        val bookType = getItem(position) ?: return
        Glide.with(holder.itemView).load(bookType.imgIcon)
            .placeholder(R.drawable.book_placeholder)
            .into(holder.itemView.bookTypeMainCoverIV)
        holder.itemView.bookTypeTV.text = bookType.type
    }
}

class BookTypeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)