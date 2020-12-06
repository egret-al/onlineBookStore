package com.onlinebookstore.mapper;

import com.onlinebookstore.entity.bookserver.BookStorage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rkc
 * @date 2020/9/17 22:05
 * @version 1.0
 */
@Mapper
public interface BookStorageMapper {

    /**
     * 更新库存信息
     * @param bookStorage BookStorage
     * @return 影响行数
     */
    int updateBookStorage(BookStorage bookStorage);

    /**
     * 新增库存信息
     * @param bookStorage 库存
     * @return 影响行数
     */
    int addBookStorage(BookStorage bookStorage);

    /**
     * 根据bookId得到库存信息
     * @param bookId 图书id
     */
    BookStorage selectStorageByBookId(Integer bookId);

    /**
     * 根据id得到库存信息
     * @param id 库存id
     */
    BookStorage selectStorageById(Integer id);

    /**
     * 根据库存id增加库存数量
     * @param id 库存id
     * @param count 增加的数量
     */
    int addStorageById(@Param("id") Integer id, @Param("count") Integer count);

    /**
     * 根据库存id减少库存数量
     * @param id 库存id
     * @param count 扣除的数量
     */
    int subtractStorageById(@Param("id") Integer id, @Param("count") Integer count);

    /**
     * 新增库存记录
     * @param bookStorage 库存对象
     */
    int insertStorage(BookStorage bookStorage);

    /**
     * 更新库存信息
     * @param bookStorage 新的库存信息
     */
    int updateStorage(BookStorage bookStorage);

    /**
     * 根据库存id删除库存信息
     * @param id 库存id
     */
    int deleteStorageById(Integer id);
}
