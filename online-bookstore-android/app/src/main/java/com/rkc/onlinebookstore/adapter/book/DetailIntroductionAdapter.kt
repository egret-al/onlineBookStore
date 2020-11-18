package com.rkc.onlinebookstore.adapter.book

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.model.book.BookResource
import kotlinx.android.synthetic.main.introduction_img_item.view.*

/**
 * 图文介绍的图片适配器
 * @author rkc
 * @date 2020/11/13 10:29
 * @version 1.0
 */
class DetailIntroductionAdapter : ListAdapter<BookResource, DetailIntroductionViewHolder>(
    DiffCallBack
) {

    object DiffCallBack : DiffUtil.ItemCallback<BookResource>() {
        override fun areItemsTheSame(oldItem: BookResource, newItem: BookResource): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: BookResource, newItem: BookResource): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailIntroductionViewHolder {
        return DetailIntroductionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.introduction_img_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DetailIntroductionViewHolder, position: Int) {
        val bookResource = getItem(position) ?: return
        //加载图片
        Glide.with(holder.itemView).load(bookResource.resourceUrl)
            .placeholder(R.drawable.book_placeholder)
            .into(holder.itemView.detailItemImageView)
    }
}

class DetailIntroductionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)