package com.rkc.onlinebookstore.adapter.home

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.model.book.Book
import kotlinx.android.synthetic.main.home_list_book_item.view.*

/**
 * @author rkc
 * @date 2020/11/11 17:36
 * @version 1.0
 */
const val BOOK_BUNDLE_KEY = "book"

class HomeBookItemAdapter : ListAdapter<Book, HomeBookItemViewHolder>(DiffCallBack) {

    object DiffCallBack : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBookItemViewHolder {
        val holder = HomeBookItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_list_book_item, parent, false))
        //创建ViewHolder时添加点击事件
        holder.itemView.setOnClickListener {
            //该book包含了bookResources，因此直接将数据存到bundle中，到详情页获取即可，不用再次请求后台服务器
            val book = getItem(holder.absoluteAdapterPosition)
            Bundle().apply {
                putParcelable(BOOK_BUNDLE_KEY, book)
                holder.itemView.findNavController().navigate(R.id.action_nav_home_to_bookDetailFragment, this)
            }
        }
        return holder
    }

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onBindViewHolder(holder: HomeBookItemViewHolder, position: Int) {
        val bookItem = getItem(position) ?: return
        with(holder.itemView) {
            shimmerGalleryLayout.apply {
                setShimmerColor(0x55FFFFFF)
                setShimmerAngle(0)
                startShimmerAnimation()
            }
            textViewBookName.text = bookItem.bookName
            textViewBookPrice.text = bookItem.price.toString() + "￥"
        }
        //加载图片
        Glide.with(holder.itemView).load(bookItem.mainCover)
            .placeholder(R.drawable.book_placeholder)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    return false
                }
                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    return false.also { holder.itemView.shimmerGalleryLayout?.stopShimmerAnimation() }
                }
            })
            .into(holder.itemView.imageViewBookMainCover)
    }
}

class HomeBookItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)