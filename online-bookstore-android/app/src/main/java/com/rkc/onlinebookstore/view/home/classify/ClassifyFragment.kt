package com.rkc.onlinebookstore.view.home.classify

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.adapter.classify.ClassifyLeftAdapter
import com.rkc.onlinebookstore.adapter.classify.ClassifyRightAdapter
import com.rkc.onlinebookstore.model.book.Book
import com.rkc.onlinebookstore.model.book.BookType
import com.rkc.onlinebookstore.viewmodel.home.classify.ClassifyViewModel
import com.rkc.onlinebookstore.viewmodel.home.classify.REQUEST_FAILURE
import com.rkc.onlinebookstore.viewmodel.home.classify.REQUEST_SUCCESSFULLY
import kotlinx.android.synthetic.main.fragment_classify.*

class ClassifyFragment : Fragment() {
    private lateinit var classifyViewModel: ClassifyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_classify, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        classifyViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(ClassifyViewModel::class.java)
//        refreshRecyclerView()
        classifyViewModel.requestStatus.observe(viewLifecycleOwner, {
            when (it) {
                REQUEST_SUCCESSFULLY -> {
                    val bookTypeList = classifyViewModel.listBookType.value
                    ArrayList<ArrayList<Book>>().apply {
                        if (bookTypeList != null) {
                            //遍历每个BookType，将里面的Books放入itemList中
                            for (bookType in bookTypeList) {
                                add(bookType.books)
                            }
                            refreshRecyclerView(bookTypeList, this)
                        }
                    }
                }
                REQUEST_FAILURE -> {
                    //TODO nothing to do
                }
            }
        })
        classifyViewModel.getAllBookType()
    }

    private fun refreshRecyclerView(typeList: List<BookType>, childList: List<List<Book>>) {
        val leftAdapter = ClassifyLeftAdapter(classifyRightRV).apply {
            submitList(typeList)
        }
        classifyLeftRV.apply {
            adapter = leftAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val rightAdapter = ClassifyRightAdapter(childList)
        classifyRightRV.apply {
            adapter = rightAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        //限制其滑动只能一次一个item
        classifyRightRV.onFlingListener = null
        PagerSnapHelper().apply { attachToRecyclerView(classifyRightRV) }
        classifyRightRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val rightLayoutManager = classifyRightRV.layoutManager as LinearLayoutManager
                val leftLayoutManager = classifyLeftRV.layoutManager as LinearLayoutManager
                //找到第一个可见的item，获取其position
                val position = rightLayoutManager.findFirstVisibleItemPosition()
                leftLayoutManager.scrollToPosition(position)
                leftAdapter.setCurrentPosition(position)
                leftAdapter.notifyDataSetChanged()
            }
        })
    }
}