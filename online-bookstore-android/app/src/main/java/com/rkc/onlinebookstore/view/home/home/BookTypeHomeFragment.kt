package com.rkc.onlinebookstore.view.home.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.adapter.home.BOOK_TYPE
import com.rkc.onlinebookstore.adapter.home.BOOK_TYPE_ID
import com.rkc.onlinebookstore.adapter.home.HomeBookItemAdapter
import com.rkc.onlinebookstore.viewmodel.home.home.BookTypeHomeViewModel
import kotlinx.android.synthetic.main.fragment_book_type_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.bookListRecyclerView

class BookTypeHomeFragment : Fragment() {
    private lateinit var bookTypeHomeViewModel: BookTypeHomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_book_type_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bookTypeId = arguments?.getInt(BOOK_TYPE_ID)!!
        //自定义viewModel构造函数
        bookTypeHomeViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(BookTypeHomeViewModel::class.java)) {
                    return BookTypeHomeViewModel(bookTypeId) as T
                }
                throw IllegalArgumentException(" unKnown ViewModel class ")
            }
        })[BookTypeHomeViewModel::class.java]


        val homeBookItemAdapter = HomeBookItemAdapter(BOOK_TYPE)
        bookListRecyclerView.apply {
            adapter = homeBookItemAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        bookTypeHomeViewModel.bookPagedList.observe(viewLifecycleOwner, {
            homeBookItemAdapter.submitList(it)
        })

//        bookTypeHomeViewModel.bookListLiveData.observe(viewLifecycleOwner, {
//            homeBookItemAdapter.submitList(it)
//            swipeRefreshLayout.isRefreshing = false
//        })
        bookTypeHomeViewModel.noData.observe(viewLifecycleOwner, {
            if (it > 0) {
                //没有数据
                remindMessageTV.visibility = View.VISIBLE
                swipeRefreshLayout.visibility = View.GONE
            } else {
                remindMessageTV.visibility = View.GONE
                swipeRefreshLayout.visibility = View.VISIBLE
            }
        })
        bookTypeHomeViewModel.fetchBooksByType(bookTypeId)
        swipeRefreshLayout.setOnRefreshListener { bookTypeHomeViewModel.fetchBooksByType(bookTypeId) }
    }
}