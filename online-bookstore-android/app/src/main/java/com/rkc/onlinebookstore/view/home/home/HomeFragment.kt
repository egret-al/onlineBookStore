package com.rkc.onlinebookstore.view.home.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.adapter.home.BookTypeListAdapter
import com.rkc.onlinebookstore.adapter.home.HomeBookItemAdapter
import com.rkc.onlinebookstore.adapter.home.NAV_HOME
import com.rkc.onlinebookstore.viewmodel.home.home.HomeViewModel
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat

const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(HomeViewModel::class.java)
        //显示工具条
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val homeBookItemAdapter = HomeBookItemAdapter(NAV_HOME)
        val bookTypeAdapter = BookTypeListAdapter()

        //recyclerView设置适配器
        bookListRecyclerView.apply {
            adapter = homeBookItemAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        bookTypeRV.apply {
            adapter = bookTypeAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
        }
        homeViewModel.bookPagedList.observe(viewLifecycleOwner, Observer { homeBookItemAdapter.submitList(it) })

        homeViewModel.bookTypeLiveData.observe(viewLifecycleOwner, {
            bookTypeAdapter.submitList(it)
        })
        homeViewModel.fetchBookTypes()

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
                titleList.add(SimpleDateFormat(DATE_FORMAT).format(banner.modifyTime))
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
//        swipeRefreshLayout.setOnRefreshListener { homeViewModel.fetchBookList() }
    }

//    /**
//     * 之所以在fragment中写菜单是因为避免搜索框出现在该Activity下的所有fragment
//     */
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        //显示工具栏菜单
//        inflater.inflate(R.menu.main_home, menu)
//        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
//        searchView.maxWidth = 1000
//        //添加文本监听器
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                //进行模糊查询显示
//                homeViewModel.fuzzyMatch(newText!!)
//                return true
//            }
//        })
//    }

    override fun onStart() {
        super.onStart()
        bookBanner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        bookBanner.stopAutoPlay()
    }
}