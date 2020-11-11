package com.rkc.onlinebookstore.view.home.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.adapter.HomeBookItemAdapter
import com.rkc.onlinebookstore.model.book.BookBanner
import com.rkc.onlinebookstore.viewmodel.home.home.HomeViewModel
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val homeBookItemAdapter = HomeBookItemAdapter()

        //recyclerView设置适配器
        bookListRecyclerView.apply {
            adapter = homeBookItemAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        //观察图书列表的数据变化
        homeViewModel.bookListLiveData.observe(viewLifecycleOwner, {
            homeBookItemAdapter.submitList(it)
            //停止刷新
            swipeRefreshLayout.isRefreshing = false
        })
        homeViewModel.fetchBookList()
        //轮播图
        homeViewModel.bookBannerLiveData.observe(viewLifecycleOwner, { it ->
            bookBanner.setImageLoader(object : ImageLoader() {
                override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
                    Glide.with(context).load(path).into(imageView)
                }
            })
            val titleList = arrayListOf<String>()
            val urlList = arrayListOf<String>()
            for (banner in it) {
                urlList.add(banner.resourceUrl)
                titleList.add(banner.modifyTime.toString())
            }
            with(bookBanner) {
                //设置轮播的动画效果
                setBannerAnimation(Transformer.Accordion)
                setImages(urlList)
                //设置banner显示样式（带标题的样式）
                setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                setBannerTitles(titleList)
                //设置指示器位置
                setIndicatorGravity(BannerConfig.CENTER)
                setDelayTime(2000)
                //添加点击监听
                setOnBannerListener { Toast.makeText(requireContext(), "你点击了第($it + 1)张图", Toast.LENGTH_SHORT).show() }
                //开始渲染
                start()
            }
        })
        //请求轮播图
        homeViewModel.fetchBookBanner()

        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener { homeViewModel.fetchBookList() }
    }

    override fun onStart() {
        super.onStart()
        bookBanner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        bookBanner.stopAutoPlay()
    }
}