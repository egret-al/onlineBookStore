package com.onlinebookstore.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.bookserver.Book;
import com.onlinebookstore.entity.bookserver.BookStorage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/12/16 11:30
 */
@Slf4j
public class BookBlockHandler {

    public static CommonplaceResult handleSelectBookByIds(List<Integer> ids, BlockException e) {
        log.error(ids.toString());
        e.printStackTrace();
        return CommonplaceResult.buildError(ids, "查询失败");
    }

    public static CommonplaceResult handleSelectAllTypeWithBook(BlockException e) {
        e.printStackTrace();
        return CommonplaceResult.buildError(e, "查询失败");
    }

    public static CommonplaceResult handleSelectAllType(BlockException e) {
        e.printStackTrace();
        return CommonplaceResult.buildError(e, "类型查询失败！");
    }

    public static CommonplaceResult handleSelectAllResourceAloneByBookId(Integer bookId, BlockException e) {
        log.error("id为{}的图书资源获取失败", bookId);
        e.printStackTrace();
        return CommonplaceResult.buildErrorNoData("资源查询失败！");
    }

    public static CommonplaceResult handleSelectAllBanner(BlockException e) {
        e.printStackTrace();
        return CommonplaceResult.buildErrorNoData("轮播图获取失败，请稍后再试！");
    }

    public static CommonplaceResult handleSelectBookAndStorageByTypePage(int id, int currentPage, int pageSize, BlockException e) {
        e.printStackTrace();
        log.error("类型id：{}  当前页：{}   每页大小：{}", id, currentPage, pageSize);
        return CommonplaceResult.buildErrorNoData("分页查询失败，请稍后再试！");
    }

    public static CommonplaceResult handleSelectAllInfoByType(int id, BlockException e) {
        e.printStackTrace();
        log.info("类型id：{}", id);
        return CommonplaceResult.buildErrorNoData("根据类型查询图书失败！");
    }

    public static CommonplaceResult handleSelectBookAndStoragePage(int currentPage, int pageSize, BlockException e) {
        e.printStackTrace();
        log.error("当前页：{}   每页大小：{}", currentPage, pageSize);
        return CommonplaceResult.buildErrorNoData("分页查询失败，请稍后再试！");
    }

    public static CommonplaceResult handleSelectAllInfo(BlockException e) {
        e.printStackTrace();
        return CommonplaceResult.buildErrorNoData("请稍后再试！");
    }

    public static CommonplaceResult handleSelectAllInfoLike(Book book, BlockException e) {
        e.printStackTrace();
        log.error(book.toString());
        return CommonplaceResult.buildErrorNoData("查询频率过高，查询失败！");
    }

    public static CommonplaceResult handleSelectBookAndType(BlockException e) {
        e.printStackTrace();
        return CommonplaceResult.buildErrorNoData("数据获取失败！请稍后再试");
    }

    public static CommonplaceResult handleSelectAllBookAndStorage(BlockException e) {
        e.printStackTrace();
        return CommonplaceResult.buildErrorNoData("数据获取失败！请稍后再试");
    }

    public static CommonplaceResult handleSelectAllBookWithResourceByType(int id, BlockException e) {
        e.printStackTrace();
        log.error("类型id：{}", id);
        return CommonplaceResult.buildErrorNoData("数据获取失败！请稍后再试");
    }

    public static CommonplaceResult handleSelectBookAndResourceLike(Book book, BlockException e) {
        e.printStackTrace();
        log.error(book.toString());
        return CommonplaceResult.buildErrorNoData("查询频率过高，查询失败！");
    }

    public static CommonplaceResult handleSelectBookAndStorageByBookId(int bookId, BlockException e) {
        e.printStackTrace();
        log.error("图书id：{}", bookId);
        return CommonplaceResult.buildError(false, "查询人数过多，查询失败！");
    }

    public static CommonplaceResult handleSelectBookAndResourceByBookId(int bookId, BlockException e) {
        e.printStackTrace();
        log.error("图书id：{}", bookId);
        return CommonplaceResult.buildErrorNoData("查询人数过多，查询失败！");
    }

    public static CommonplaceResult handleSelectAllBookAloneById(int bookId, BlockException e) {
        e.printStackTrace();
        log.error("图书id：{}", bookId);
        return CommonplaceResult.buildErrorNoData("查询人数过多，查询失败！");
    }

    public static CommonplaceResult handleSelectBookContainAllInfoById(int bookId, BlockException e) {
        e.printStackTrace();
        log.error("图书id：{}", bookId);
        return CommonplaceResult.buildErrorNoData("查询人数过多，查询失败！");
    }

    public static CommonplaceResult handleSelectStorageByBookId(int bookId, BlockException e) {
        e.printStackTrace();
        log.error("图书id：{}", bookId);
        return CommonplaceResult.buildErrorNoData("查询人数过多，查询失败！");
    }

    public static CommonplaceResult handleUpdateBookStorage(BookStorage bookStorage, BlockException e) {
        e.printStackTrace();
        log.error(bookStorage.toString());
        return CommonplaceResult.buildErrorNoData("操作失败，请稍后再试！");
    }

    public static CommonplaceResult handleAddStorageById(Map<String, Integer> pojo, BlockException e) {
        e.printStackTrace();
        log.error(pojo.toString());
        return CommonplaceResult.buildErrorNoData("库存添加失败！");
    }

    public static CommonplaceResult handleSubtractStorageById(Map<String, Integer> pojo, BlockException e) {
        e.printStackTrace();
        log.error(pojo.toString());
        return CommonplaceResult.buildError(false, "库存扣减失败！");
    }
}
