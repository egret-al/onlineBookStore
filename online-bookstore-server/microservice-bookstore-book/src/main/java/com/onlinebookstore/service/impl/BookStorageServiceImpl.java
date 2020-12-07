package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.bookserver.BookStorage;
import com.onlinebookstore.mapper.BookStorageMapper;
import com.onlinebookstore.service.BookStorageService;
import com.onlinebookstore.util.RedisUtils;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

import static com.onlinebookstore.util.bookutil.BookConstantPool.*;

/**
 * @author rkc
 * @date 2020/9/20 10:19
 * @version 2.0
 */
@Slf4j
@Service
public class BookStorageServiceImpl implements BookStorageService {

    @Resource
    private BookStorageMapper bookStorageMapper;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 根据bookId得到库存信息
     * @param bookId 图书id
     */
    @Override
    public CommonplaceResult selectStorageByBookId(Integer bookId) {
        BookStorage bookStorage = bookStorageMapper.selectStorageByBookId(bookId);
        return ObjectUtils.isEmpty(bookStorage) ? CommonplaceResult.buildErrorNoData("没有该数据")
                : CommonplaceResult.buildSuccessNoMessage(bookStorage);
    }

    /**
     * 根据id得到库存信息
     * @param id 库存id
     */
    @Override
    public CommonplaceResult selectStorageById(Integer id) {
        BookStorage bookStorage = bookStorageMapper.selectStorageById(id);
        return ObjectUtils.isEmpty(bookStorage) ? CommonplaceResult.buildErrorNoData("没有该数据")
                : CommonplaceResult.buildSuccessNoMessage(bookStorage);
    }

    /**
     * 更新库存信息
     * @param bookStorage BookStorage
     * @return CommonplaceResult
     */
    @Override
    public CommonplaceResult updateBookStorage(BookStorage bookStorage) {
        delRelativeCache(bookStorage);
        if (bookStorageMapper.updateBookStorage(bookStorage) > 0) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            delRelativeCache(bookStorage);
            return CommonplaceResult.buildSuccessNoData("操作成功！");
        }
        return CommonplaceResult.buildErrorNoData("操作失败！");
    }

    /**
     * 根据库存id增加库存数量
     * @param id 库存id
     * @param count 增加的数量
     */
    @Override
    public CommonplaceResult addStorageById(Integer id, Integer count) {
        return modifyStorageCount(id, count, 0);
    }

    private CommonplaceResult modifyStorageCount(Integer id, Integer count, Integer operation) {
        int row = 0;
        BookStorage bookStorage = new BookStorage();
        bookStorage.setBookId(id);
        delRelativeCache(bookStorage);
        if (operation == 0) {
            //增加操作
            row = bookStorageMapper.addStorageById(id, count);
        } else if (operation == 1) {
            //减少操作
            row = bookStorageMapper.subtractStorageById(id, count);
        }
        if (row > 0) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            delRelativeCache(bookStorage);
            return CommonplaceResult.buildSuccess(true, "修改成功！");
        }
        return CommonplaceResult.buildError(false, "非法操作！");
    }

    /**
     * 根据库存id减少库存数量
     * @param id 库存id
     * @param count 扣除的数量
     */
    @Override
    @Transactional
    public CommonplaceResult subtractStorageById(Integer id, Integer count) {
        log.info("book-server，xid：" + RootContext.getXID());
        return modifyStorageCount(id, count, 1);
    }

    private CommonplaceResult modifyStorage(Object pojo, Integer operation) {
        int row = 0;
        if (pojo instanceof BookStorage && operation == 1) {
            //增加操作
            row = bookStorageMapper.insertStorage((BookStorage) pojo);
        } else if (pojo instanceof BookStorage && operation == 2) {
            //更新操作
            BookStorage bookStorage = (BookStorage) pojo;
            delRelativeCache(bookStorage);
            row = bookStorageMapper.updateStorage(bookStorage);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            delRelativeCache(bookStorage);
        } else if (pojo instanceof Integer && operation == 3) {
            //删除操作
            BookStorage bookStorage = new BookStorage();
            bookStorage.setId((Integer) pojo);
            delRelativeCache(bookStorage);
            row = bookStorageMapper.deleteStorageById((Integer) pojo);
        }
        return row == 0 ? CommonplaceResult.buildErrorNoData("操作失败！") : CommonplaceResult.buildSuccessNoData("操作成功！");
    }

    /**
     * 新增库存记录
     * @param bookStorage 库存对象
     */
    @Override
    public CommonplaceResult insertStorage(BookStorage bookStorage) {
        return modifyStorage(bookStorage, 1);
    }

    /**
     * 更新库存信息
     * @param bookStorage 新的库存信息
     */
    @Override
    public CommonplaceResult updateStorage(BookStorage bookStorage) {
        return modifyStorage(bookStorage, 2);
    }

    /**
     * 根据库存id删除库存信息
     * @param id 库存id
     */
    @Override
    public CommonplaceResult deleteStorageById(Integer id) {
        return modifyStorage(id, 3);
    }

    private void delRelativeCache(BookStorage bookStorage) {
        redisUtils.del(SELECT_ALL_BOOK_INFO, SELECT_ALL_BOOK_INFO_TYPE, SELECT_ALL_BOOK_INFO_BY_BOOK_ID + bookStorage.getBookId(),
                SELECT_ALL_BOOK_WITH_STORAGE_BY_BOOK_ID + bookStorage.getBookId());
    }
}
