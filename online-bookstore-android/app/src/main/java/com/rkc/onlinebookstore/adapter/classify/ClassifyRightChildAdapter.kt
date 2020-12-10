package com.rkc.onlinebookstore.adapter.classify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.adapter.home.BOOK_BUNDLE_KEY
import com.rkc.onlinebookstore.model.book.Book
import kotlinx.android.synthetic.main.classify_right_child_item.view.*

/**
 * @author rkc
 * @date 2020/11/14 17:24
 * @version 1.0
 */
class ClassifyRightChildAdapter(private var bookList: List<Book>) : RecyclerView.Adapter<ClassifyRightChildViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassifyRightChildViewHolder {
        val holder = ClassifyRightChildViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.classify_right_child_item, parent, false))
        holder.itemView.setOnClickListener {
            val book = bookList[holder.absoluteAdapterPosition]
            Bundle().apply {
                putParcelable(BOOK_BUNDLE_KEY, book)
                //跳转到详情页
                holder.itemView.findNavController().navigate(R.id.action_nav_classify_to_bookFragment, this)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ClassifyRightChildViewHolder, position: Int) {
        val book = bookList[position]
        //数据绑定
        holder.itemView.bookNameTV.text = book.bookName
        Glide.with(holder.itemView).load(book.mainCover)
            .placeholder(R.drawable.book_placeholder)
            .into(holder.itemView.bookMainCoverImg)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}

class ClassifyRightChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)