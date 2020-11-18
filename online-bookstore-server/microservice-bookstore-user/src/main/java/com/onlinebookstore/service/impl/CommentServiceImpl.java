package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.Comment;
import com.onlinebookstore.mapper.CommentMapper;
import com.onlinebookstore.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/17 19:44
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;

    /**
     * 查询全部评论
     * @return CommonplaceResult
     */
    @Override
    public CommonplaceResult selectAllComment() {
        return CommonplaceResult.buildSuccessNoMessage(commentMapper.selectAllComment());
    }

    /**
     * 查询指定书的全部评论
     * @param bookId bookId
     * @return CommonplaceResult
     */
    @Override
    public CommonplaceResult selectCommentsByBookId(int bookId) {
        return CommonplaceResult.buildSuccessNoMessage(commentMapper.selectCommentsByBookId(bookId));
    }

    /**
     * 添加评论
     * @param comment comment
     * @return 影响行数
     */
    @Override
    public CommonplaceResult insertComment(Comment comment) {
        return commentMapper.insertComment(comment) > 0 ? CommonplaceResult.buildSuccessNoData("插入成功") : CommonplaceResult.buildErrorNoData("插入失败");
    }
}
