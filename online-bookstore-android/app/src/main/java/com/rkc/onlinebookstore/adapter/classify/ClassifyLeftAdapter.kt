package com.rkc.onlinebookstore.adapter.classify

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.model.book.BookType
import kotlinx.android.synthetic.main.classify_left_item.view.*

/**
 * @author rkc
 * @date 2020/11/14 16:37
 * @version 1.0
 */
class ClassifyLeftAdapter(private var rightRecyclerView: RecyclerView) : ListAdapter<BookType, ClassifyLeftViewHolder>(DiffCallBack) {
    private var currentPosition = -1

    object DiffCallBack : DiffUtil.ItemCallback<BookType>() {
        override fun areItemsTheSame(oldItem: BookType, newItem: BookType): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: BookType, newItem: BookType): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassifyLeftViewHolder {
        val holder = ClassifyLeftViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.classify_left_item, parent, false))
        //添加点击事件，联动右侧的RecyclerView
        holder.itemView.setOnClickListener {
            //得到当前的位置并设置RecyclerView滚动到对应的位置
            setCurrentPosition(holder.absoluteAdapterPosition)
//            rightRecyclerView.scrollToPosition(holder.absoluteAdapterPosition)
            rightRecyclerView.smoothScrollToPosition(holder.absoluteAdapterPosition)
            notifyDataSetChanged()
        }
        return holder
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ClassifyLeftViewHolder, position: Int) {
        val bookType = getItem(position) ?: return
        holder.itemView.classifyTextViewTypeName.text = bookType.type
        if (currentPosition in 0 until itemCount && currentPosition == position) {
            holder.itemView.classifyTextViewTypeName.setTextColor(holder.itemView.resources.getColor(R.color.left_font_selected))
            holder.itemView.classifyLeftLayout.setBackgroundColor(holder.itemView.resources.getColor(R.color.left_background_selected))
        } else {
            holder.itemView.classifyLeftLayout.setBackgroundColor(holder.itemView.resources.getColor(R.color.left_background_normal))
            holder.itemView.classifyTextViewTypeName.setTextColor(holder.itemView.resources.getColor(R.color.left_font_normal))
        }
    }

    /**
     * 设置当前显示position
     */
    fun setCurrentPosition(position: Int) {
        currentPosition = position
    }
}

class ClassifyLeftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)