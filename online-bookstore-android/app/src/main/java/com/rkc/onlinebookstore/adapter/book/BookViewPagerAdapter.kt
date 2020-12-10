package com.rkc.onlinebookstore.adapter.book

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * @author rkc
 * @date 2020/12/10 10:12
 * @version 1.0
 */
class BookViewPagerAdapter(fm: FragmentManager, behavior: Int, private val fragments: List<Fragment>,
                           private val titleList: List<String>) : FragmentStatePagerAdapter(fm, behavior) {

    override fun getPageTitle(position: Int): CharSequence {
        return titleList[position]
    }
    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
}