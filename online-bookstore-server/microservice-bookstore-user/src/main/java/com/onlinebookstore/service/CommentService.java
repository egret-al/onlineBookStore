package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.Comment;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/17 19:42
 */
public interface CommentService {

    /**
     * 查询所有评论
     * @return CommonplaceResult
     */
    CommonplaceResult selectAllComment();

    /**
     * 根据bookId查询所有评论
     * @param id 图书id
     * @return CommonplaceResult
     */
    CommonplaceResult selectCommentsByBookId(int id);

    /**
     * 增加评论
     * @param comment comment
     * @return 影响行数
     */
    CommonplaceResult insertComment(Comment comment);
}
