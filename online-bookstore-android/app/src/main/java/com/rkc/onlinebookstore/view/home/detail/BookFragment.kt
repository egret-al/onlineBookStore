package com.rkc.onlinebookstore.view.home.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.adapter.book.BookViewPagerAdapter
import com.rkc.onlinebookstore.adapter.home.BOOK_BUNDLE_KEY
import com.rkc.onlinebookstore.model.book.Book
import kotlinx.android.synthetic.main.common_title.*
import kotlinx.android.synthetic.main.fragment_book.*

class BookFragment : Fragment() {
    private val titleList = arrayListOf("图书", "详情", "评价")
    private val fragmentList = arrayListOf<Fragment>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_book, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        backIV.setOnClickListener { findNavController().navigateUp() }

        val book = arguments?.getParcelable<Book>(BOOK_BUNDLE_KEY)
        fragmentList.apply {
            add(BookDetailFragment(book!!))
            add(IntroductionFragment(book))
            add(CommentFragment(book))
        }
        val bookViewPagerAdapter = BookViewPagerAdapter(childFragmentManager, 0, fragmentList, titleList)
        viewPager.offscreenPageLimit = fragmentList.size
        viewPager.adapter = bookViewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentList.clear()
    }
}