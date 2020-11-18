package com.rkc.onlinebookstore.view.home.detail

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.fragment_book_detail.shimmerLayoutMainCover
import kotlinx.android.synthetic.main.fragment_introduction.*

class IntroductionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_introduction, container, false)
    }

    @SuppressLint("SetTextI18n")
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
        val book = arguments?.getParcelable<Book>(BOOK_BUNDLE_KEY)
        //recyclerView
        val detailIntroductionAdapter = DetailIntroductionAdapter().apply { submitList(book?.bookResources) }
        introductionRecyclerViewImgList.apply {
            adapter = detailIntroductionAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        //加载内容
        Glide.with(this).load(book?.mainCover)
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
        introductionTextViewBookName.text = book?.bookName
        introductionTextViewAuthor.text = book?.author
        introductionTextViewISBN.text = book?.isbn
        introductionTextViewDescription.text = "    " + book?.description
        introductionTextViewPublishTime.text = book?.createTime.toString()
    }
}