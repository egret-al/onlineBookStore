package com.rkc.onlinebookstore.view.home.detail

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.adapter.home.BOOK_BUNDLE_KEY
import com.rkc.onlinebookstore.adapter.book.DetailIntroductionAdapter
import com.rkc.onlinebookstore.model.book.Book
import com.rkc.onlinebookstore.view.home.home.DATE_FORMAT
import com.rkc.onlinebookstore.viewmodel.home.detail.IntroductionViewModel
import kotlinx.android.synthetic.main.fragment_book_detail.shimmerLayoutMainCover
import kotlinx.android.synthetic.main.fragment_introduction.*
import java.text.SimpleDateFormat

class IntroductionFragment(private val book: Book) : Fragment() {
    private lateinit var introductionViewModel: IntroductionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_introduction, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        shimmerLayoutMainCover.apply {
            setShimmerColor(0x55FFFFFF)
            setShimmerAngle(0)
            startShimmerAnimation()
        }
        shimmerLayoutImgItem.apply {
            setShimmerColor(0x55FFFFFF)
            setShimmerAngle(0)
            startShimmerAnimation()
        }
        introductionViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(IntroductionViewModel::class.java)
        //检查book是否有需要的资源
        if (book.bookResources == null) {
            introductionViewModel.checkBookResource(book)
            introductionViewModel.completeBook.observe(viewLifecycleOwner, {
                Log.d("tag", it.bookResources.toString())
                load(it)
            })
        } else {
            load(book)
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun load(book: Book) {
        //recyclerView
        val detailIntroductionAdapter = DetailIntroductionAdapter().apply { submitList(book.bookResources) }
        introductionRecyclerViewImgList.apply {
            adapter = detailIntroductionAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        //加载内容
        Glide.with(this).load(book.mainCover)
            .placeholder(R.drawable.book_placeholder)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    return false
                }
                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    return false.also { shimmerLayoutMainCover?.stopShimmerAnimation() }
                }
            })
            .into(introductionImageViewMainCover)
        introductionTextViewBookName.text = book.bookName
        introductionTextViewAuthor.text = book.author
        introductionTextViewISBN.text = book.isbn
        introductionTextViewDescription.text = "    " + book.description
        introductionTextViewPublishTime.text = SimpleDateFormat(DATE_FORMAT).format(book.createTime)
    }
}