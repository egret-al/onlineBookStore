package com.rkc.onlinebookstore.view.home.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.adapter.comment.CommentsAdapter
import com.rkc.onlinebookstore.adapter.home.BOOK_BUNDLE_KEY
import com.rkc.onlinebookstore.model.book.Book
import com.rkc.onlinebookstore.model.user.Comment
import com.rkc.onlinebookstore.viewmodel.home.detail.CommentViewModel
import kotlinx.android.synthetic.main.fragment_comment.*

class CommentFragment(private val book: Book) : Fragment() {
    private lateinit var commentViewModel: CommentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        commentViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(CommentViewModel::class.java)
//        commentViewModel.book = arguments?.getParcelable(BOOK_BUNDLE_KEY)!!
        commentViewModel.book = book

        commentListRV.layoutManager = LinearLayoutManager(requireContext())
        commentViewModel.commentLiveData.observe(viewLifecycleOwner, { refresh(it) })
        commentViewModel.sendStatus.observe(viewLifecycleOwner, {
            if (commentViewModel.commentLiveData.value != null) {
                //重新请求获取数据
                commentViewModel.fetchComments()
            }
        })
        commentViewModel.fetchComments()

        //发送评论
        sendCommentContent.setOnClickListener {
            val content = inputCommentContent.text.toString()
            if (content != "") {
                commentViewModel.sendComment(content)
            }
        }
    }

    private fun refresh(commentList: List<Comment>) {
        Log.d("tag", "适配器刷新")
        if (commentList.isEmpty()) {
            noComment.visibility = View.VISIBLE
        } else {
            //存在数据则不显示“暂无评论”，给recyclerView添加适配器
            noComment.visibility = View.GONE
            commentListRV.adapter = CommentsAdapter().apply { submitList(commentList) }
        }
    }
}