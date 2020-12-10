package com.rkc.onlinebookstore.view.home.order

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.adapter.home.BOOK_BUNDLE_KEY
import com.rkc.onlinebookstore.model.book.Book
import com.rkc.onlinebookstore.model.order.ORDER_EXPIRE
import com.rkc.onlinebookstore.model.order.ORDER_FINISHED
import com.rkc.onlinebookstore.model.order.ORDER_UN_PAYMENT
import com.rkc.onlinebookstore.model.order.Order
import com.rkc.onlinebookstore.view.home.home.DATE_FORMAT
import com.rkc.onlinebookstore.viewmodel.home.detail.ORDER_CREATED_KEY
import com.rkc.onlinebookstore.viewmodel.home.order.*
import kotlinx.android.synthetic.main.common_title.*
import kotlinx.android.synthetic.main.fragment_unpaid_order.*
import java.text.SimpleDateFormat

class UnpaidOrderFragment : Fragment() {
    private lateinit var unpaidOrderViewModel: UnpaidOrderViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_unpaid_order, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        titleTV.text = "待支付"
        backIV.setOnClickListener { findNavController().navigateUp() }
        unpaidOrderViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(UnpaidOrderViewModel::class.java)
        //获取order和book
        val order = arguments?.getParcelable<Order>(ORDER_CREATED_KEY)
        val book = arguments?.getParcelable<Book>(BOOK_BUNDLE_KEY)
        initDemonstration(order!!, book!!)

        unpaidOrderViewModel.orderCancelStatus.observe(viewLifecycleOwner, {
            when (it) {
                ORDER_CANCEL_SUCCESSFULLY -> {
                    Toast.makeText(requireContext(), "取消成功！", Toast.LENGTH_SHORT).show()
                    //完成取消，导航到首页
                    findNavController().navigate(R.id.action_unpaidOrderFragment_to_nav_home)
                }
                ORDER_CANCEL_FAILURE -> { Toast.makeText(requireContext(), "取消失败，网络异常！", Toast.LENGTH_SHORT).show() }
            }
        })

        unpaidOrderViewModel.orderFinishStatus.observe(viewLifecycleOwner, {
            when (it) {
                ORDER_FINISH_SUCCESSFULLY -> {
                    Toast.makeText(requireContext(), "支付成功！", Toast.LENGTH_SHORT).show()
                    //订单完成，导航到首页
                    findNavController().navigate(R.id.action_unpaidOrderFragment_to_nav_home)
                }
                ORDER_FINISH_FAILURE -> { Toast.makeText(requireContext(), unpaidOrderViewModel.orderFinishMessage.value, Toast.LENGTH_SHORT).show() }
            }
        })
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun initDemonstration(order: Order, book: Book) {
        //初始化显示
        unpaidTextViewBookName.text = book.bookName
        unpaidTextViewAuthor.text = book.author
        unpaidTextViewISBN.text = book.isbn
        unpaidTextViewPublishTime.text = SimpleDateFormat(DATE_FORMAT).format(book.createTime)
        unpaidTextViewPublisher.text = book.publisher
        unpaidTextViewPrice.text = "￥${book.price}"
        //加载图片
        Glide.with(this).load(book.mainCover)
            .placeholder(R.drawable.book_placeholder)
            .into(unpaidImageViewMainCover)

        receiverTV.text = order.receiverName
        receivingAddress.text = order.address
        contactPhoneTV.text = order.phone
        unpaidTextViewSerialNumber.text = order.serialNumber
        unpaidTextViewOrderCreateTime.text = SimpleDateFormat(DATE_FORMAT).format(order.createTime)
        unpaidTextViewProductCount.text = "${order.productCount}本"
        when (order.orderPaymentStatus) {
            ORDER_EXPIRE -> {
                unpaidTextViewStatus.setTextColor(resources.getColor(R.color.deep_gray))
                unpaidTextViewStatus.text = "已过期"
                unpaidButtonPurchase.isEnabled = false
                //按钮删除事件
                unpaidButtonCancelOrder.apply {
                    text = "删除订单"
                    setOnClickListener {
                        //TODO 删除订单请求
                    }
                }
            }
            ORDER_FINISHED -> {
                unpaidTextViewStatus.setTextColor(resources.getColor(R.color.green))
                unpaidTextViewStatus.text = "已完成"
                unpaidButtonCancelOrder.isEnabled = false
                unpaidButtonPurchase.isEnabled = false
            }
            ORDER_UN_PAYMENT -> {
                unpaidTextViewStatus.setTextColor(resources.getColor(R.color.red))
                unpaidTextViewStatus.text = "未支付"
                //取消订单
                unpaidButtonCancelOrder.setOnClickListener { unpaidOrderViewModel.cancelOrder(order.serialNumber) }
                //购买
                unpaidButtonPurchase.setOnClickListener { unpaidOrderViewModel.purchase(order.serialNumber) }
            }
        }
    }
}