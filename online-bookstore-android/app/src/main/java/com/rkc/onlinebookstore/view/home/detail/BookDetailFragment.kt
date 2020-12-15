package com.rkc.onlinebookstore.view.home.detail

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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
import com.rkc.onlinebookstore.view.home.home.DATE_FORMAT
import com.rkc.onlinebookstore.viewmodel.home.detail.*
import kotlinx.android.synthetic.main.fragment_book_detail.*
import java.text.SimpleDateFormat
import java.util.ArrayList

class BookDetailFragment(private val book: Book) : Fragment() {
    private lateinit var bookDetailViewModel: BookDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bookDetailViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory(
                requireActivity().application
            )
        ).get(BookDetailViewModel::class.java)
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
        //选择收货地址
        showAddressDialog.setOnClickListener {
            val keys = ArrayList<Int>()
            val values = ArrayList<String>()
            bookDetailViewModel.bindAddresses.value?.forEach { value ->
                run {
                    values.add(value.address)
                    keys.add(value.id)
                }
            }
            AlertDialog.Builder(requireContext()).apply {
                setTitle("选择收货地址")
                setSingleChoiceItems(values.toTypedArray(), -1) { dialog, which ->
                    bookDetailViewModel.bindAddresses.value?.forEach { value ->
                        run {
                            if (value.id == keys[which]) {
                                bookDetailViewModel.selectedAddress.postValue(value)
                                Toast.makeText(requireContext(), "收件人：${value.receiverName}\n联系电话：${value.phone}\n收货地址：${value.address}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    dialog?.dismiss()
                }
            }.show()
        }
        bookDetailViewModel.selectedAddress.observe(viewLifecycleOwner, {
            selectedAddress.text = it.address
        })
        //立即购买
        detailButtonPurchase.setOnClickListener {
            //避免重复点击
            detailButtonPurchase.isEnabled = false
            //发起下单请求
            bookDetailViewModel.immediatelyPurchase(bookDetailViewModel.book.value)
        }
        //加入购物车
        detailButtonAddToCart.setOnClickListener { bookDetailViewModel.addToShoppingTrolley() }

        detailButtonAdd.setOnClickListener { bookDetailViewModel.book.value?.bookStorage?.residueCount?.let { it1 -> bookDetailViewModel.addCount(
            it1
        ) } }
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
                    findNavController().navigate(
                        R.id.action_bookFragment_to_unpaidOrderFragment,
                        bundle
                    )
                    bookDetailViewModel.resetNetRequestStatus()
                }
                REQUESTING -> {
                    detailButtonPurchase.isEnabled = true
                }
            }
        })
        //观察是否存在默认收货地址
        bookDetailViewModel.hasDefaultAddress.observe(viewLifecycleOwner, {
            if (!it) {
                findNavController().navigate(R.id.action_bookFragment_to_addressFragment)
                //恢复初始属性，避免无法返回
                bookDetailViewModel.hasDefaultAddress.value = true
            }
        })

        bookDetailViewModel.addSuccess.observe(viewLifecycleOwner, {
            if (it != 0) Toast.makeText(
                requireContext(),
                "添加成功！",
                Toast.LENGTH_SHORT
            ).show()
        })
        bookDetailViewModel.addFailure.observe(viewLifecycleOwner, {
            if (it != 0) Toast.makeText(
                requireContext(),
                "不能重复添加！",
                Toast.LENGTH_SHORT
            ).show()
        })
        bookDetailViewModel.getDefaultAddress()
        bookDetailViewModel.selectAddresses()
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun load(book: Book?) {
        //加载图片
        Glide.with(this).load(book?.mainCover)
            .placeholder(R.drawable.book_placeholder)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
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
        detailTextViewPublishTime.text = SimpleDateFormat(DATE_FORMAT).format(book!!.createTime)
    }
}