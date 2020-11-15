package com.rkc.onlinebookstore.adapter.classify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.model.book.Book
import kotlinx.android.synthetic.main.classify_right_item.view.*

/**
 * 右侧的一级adapter每行显示一个，二级子adapter每行显示3个
 * @author rkc
 * @date 2020/11/14 16:37
 * @version 1.0
 */
class ClassifyRightAdapter(private var classifyRightChildList: List<List<Book>>) :
    RecyclerView.Adapter<ClassifyRightChildListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassifyRightChildListViewHolder {
        return ClassifyRightChildListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.classify_right_item, parent, false))
    }

    override fun onBindViewHolder(holder: ClassifyRightChildListViewHolder, position: Int) {
        //得到list的子list，作为数据源给每个分类的adapter
        val childList = classifyRightChildList[position]
        //设置子adapter
        holder.itemView.classifyRightChildListRV.apply {
            adapter = ClassifyRightChildAdapter(childList)
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    override fun getItemCount(): Int {
        return classifyRightChildList.size
    }
}

class ClassifyRightChildListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)