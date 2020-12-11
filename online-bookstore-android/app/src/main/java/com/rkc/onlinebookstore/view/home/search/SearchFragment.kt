package com.rkc.onlinebookstore.view.home.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.adapter.search.SearchBookListAdapter
import com.rkc.onlinebookstore.viewmodel.home.search.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        searchViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(SearchViewModel::class.java)
        val searchBookListAdapter = SearchBookListAdapter()
        recyclerView.apply {
            adapter = searchBookListAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        searchViewModel.bookListLiveData.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                searchStatus.visibility = View.GONE
                searchStatus.text = ""
                searchBookListAdapter.submitList(it)
            } else if (it.isEmpty()) {
                Toast.makeText(requireContext(), "没有搜索到该商品", Toast.LENGTH_SHORT).show()
            }
        })
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isEmpty() == false) {
                    searchViewModel.fuzzyMatch(newText)
                }
                return true
            }
        })
    }
}