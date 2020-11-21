package com.rkc.onlinebookstore.view.home.detail

import android.annotation.SuppressLint
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.adapter.home.BOOK_BUNDLE_KEY
import com.rkc.onlinebookstore.model.book.Book
import com.rkc.onlinebookstore.viewmodel.home.detail.*
import kotlinx.android.synthetic.main.fragment_book_detail.*

class BookDetailFragment : Fragment() {
    private lateinit var bookDetailViewModel: BookDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bookDetailViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(BookDetailViewModel::class.java)
        return inflater.inflate(R.layout.fragment_book_detail, container, false)
    }

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        shimmerLayoutMainCover.apply {
            setShimmerColor(0x55FFFFFF)
            setShimmerAngle(0)
            startShimmerAnimation()
        }
        //从bundle中获取数据
        val book = arguments?.getParcelable<Book>(BOOK_BUNDLE_KEY)
        bookDetailViewModel.setBook(book)
        //购买数量
        bookDetailViewModel.sellingCountLiveData.observe(viewLifecycleOwner, {
            detailTextViewPurchaseCount.text = it.toString()
        })
        //是否使用积分
        detailSwitchUseScore.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                bookDetailViewModel.modifyUseScoreStatus(1)
            } else {
                bookDetailViewModel.modifyUseScoreStatus(0)
            }
        }

        //立即购买
        detailButtonPurchase.setOnClickListener {
            //避免重复点击
            detailButtonPurchase.isEnabled = false
            //发起下单请求
            bookDetailViewModel.immediatelyPurchase(bookDetailViewModel.book.value)
        }
        //图文介绍
        detailButtonIntroduce.setOnClickListener {
            Bundle().apply {
                putParcelable(BOOK_BUNDLE_KEY, bookDetailViewModel.book.value)
                findNavController().navigate(R.id.action_bookDetailFragment_to_introductionFragment, this)
            }
        }
        //加入购物车
        detailButtonAddToCart.setOnClickListener {  }
        //查看评论
        detailButtonComment.setOnClickListener {
            Bundle().apply {
                putParcelable(BOOK_BUNDLE_KEY, bookDetailViewModel.book.value)
                findNavController().navigate(R.id.action_bookDetailFragment_to_commentFragment, this)
            }
        }

        detailButtonAdd.setOnClickListener { bookDetailViewModel.book.value?.bookStorage?.residueCount?.let { it1 -> bookDetailViewModel.addCount(it1) } }
        detailButtonSub.setOnClickListener { bookDetailViewModel.subCount() }

        bookDetailViewModel.book.observe(viewLifecycleOwner, {
            if (it?.bookStorage != null) {
                //数据存在，加载视图
                load(it)
            }
        })
        //观察创建的订单，如果创建成功且网络请求没有问题，则进行页面导航跳转
        bookDetailViewModel.orderCreated.observe(viewLifecycleOwner, {
            when (bookDetailViewModel.purchaseNetRequestSuccessful.value) {
                REQUEST_ERROR -> {
                    Toast.makeText(requireContext(), "订单创建失败！", Toast.LENGTH_SHORT).show()
                    detailButtonPurchase.isEnabled = true
                }
                REQUEST_SUCCESS -> {
                    Toast.makeText(requireContext(), "订单创建成功！", Toast.LENGTH_SHORT).show()
                    detailButtonPurchase.isEnabled = true
                    //准备数据跳转到订单支付
                    val bundle = Bundle().apply {
                        //订单和图书都加入bundle
                        putParcelable(ORDER_CREATED_KEY, it)
                        putParcelable(BOOK_BUNDLE_KEY, book)
                    }
                    findNavController().navigate(R.id.action_bookDetailFragment_to_unpaidOrderFragment, bundle)
                }
                REQUESTING -> { detailButtonPurchase.isEnabled = true }
            }
        })
        //观察是否存在默认收货地址
        bookDetailViewModel._hasDefaultAddress.observe(viewLifecycleOwner, {
            if (!it) {
                findNavController().navigate(R.id.action_bookDetailFragment_to_addressFragment)
                //恢复初始属性，避免无法返回
                bookDetailViewModel._hasDefaultAddress.value = true
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun load(book: Book?) {
        //加载图片
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
            .into(imageViewMainCover)
        //加载内容
        detailTextViewBookName.text = book?.bookName
        detailTextViewMarketPrice.apply {
            //设置删除线
            paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            text = "￥${book?.price?.plus(20)}"
        }
        detailTextViewSellingPrice.text = "￥${book?.price}"
        detailTextViewResidue.text = "${book?.bookStorage?.residueCount}"
        detailTextViewAuthor.text = book?.author
        detailTextViewISBN.text = book?.isbn
        detailTextViewPublisher.text = book?.publisher
        detailTextViewPublishTime.text = book?.createTime.toString()
    }
}