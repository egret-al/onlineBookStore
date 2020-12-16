package com.onlinebookstore.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.Comment;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/12/16 11:08
 */
@Slf4j
public class CommentBlockHandler {

    public static CommonplaceResult handleSelectCommentsByBookId(Integer bookId, BlockException blockException) {
        log.error(blockException.getMessage());
        return CommonplaceResult.buildErrorNoData("请求人数过多，请稍后再试！");
    }

    public static CommonplaceResult handleInsertComment(Comment comment, BlockException blockException) {
        log.error(blockException.getMessage());
        return CommonplaceResult.buildErrorNoData("评论失败，请稍后再试！");
    }
}
