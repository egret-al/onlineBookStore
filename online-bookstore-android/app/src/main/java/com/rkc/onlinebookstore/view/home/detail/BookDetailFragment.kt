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
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.adapter.BOOK_BUNDLE_KEY
import com.rkc.onlinebookstore.model.book.Book
import com.rkc.onlinebookstore.viewmodel.home.detail.*
import kotlinx.android.synthetic.main.fragment_book_detail.*
import kotlinx.android.synthetic.main.home_list_book_item.view.*

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
        bookDetailViewModel.book.observe(viewLifecycleOwner, {
            Log.d("tag", "book observe")
            if (it?.bookStorage != null) {
                //数据存在，加载视图
                Log.d("load", it.bookResources.toString())
                load(it)
                Log.d("tag", "视图刷新完成")
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun load(book: Book?) {
        Log.d("loading", book.toString())
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
        detailButtonAdd.setOnClickListener { book?.bookStorage?.residueCount?.let { it1 -> bookDetailViewModel.addCount(it1) } }
        detailButtonSub.setOnClickListener { bookDetailViewModel.subCount() }

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
                        //订单和图书都让入bundle
                        putParcelable(ORDER_CREATED_KEY, it)
                        putParcelable(BOOK_BUNDLE_KEY, book)
                    }
//                    view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_bookDetailFragment_to_unpaidOrderFragment, bundle) }
                    //TODO bug待修复 加载了两次，说明load函数被调用了两次
                    findNavController().navigate(R.id.action_bookDetailFragment_to_unpaidOrderFragment, bundle)
                }
                REQUESTING -> { detailButtonPurchase.isEnabled = true }
            }
        })

        //立即购买
        detailButtonPurchase.setOnClickListener {
            //避免重复点击
            detailButtonPurchase.isEnabled = false
            //发起下单请求
            bookDetailViewModel.immediatelyPurchase(book)
        }
        //图文介绍
        detailButtonIntroduce.setOnClickListener {
            Bundle().apply {
                putParcelable(BOOK_BUNDLE_KEY, book)
                findNavController().navigate(R.id.action_bookDetailFragment_to_introductionFragment, this)
            }
        }
        //加入购物车
        detailButtonAddToCart.setOnClickListener {  }
        //查看评论
        detailButtonComment.setOnClickListener {  }
    }
}