package com.onlinebookstore.mapper;

import com.onlinebookstore.entity.userserver.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/17 17:33
 */
@Mapper
public interface CommentMapper {

    /**
     * 查询所有评论
     * @return 所有评论，映射了user信息
     */
    List<Comment> selectAllComment();

    /**
     * 根据bookId查询所有评论
     * @param id bookId
     * @return 图书的评论，映射了user信息
     */
    List<Comment> selectCommentsByBookId(Integer id);

    /**
     * 增加评论
     * @param comment comment
     * @return  影响行数
     */
    int insertComment(Comment comment);
}
