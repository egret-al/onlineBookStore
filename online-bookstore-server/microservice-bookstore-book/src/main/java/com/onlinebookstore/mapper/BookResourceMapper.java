package com.onlinebookstore.mapper;

import com.onlinebookstore.entity.bookserver.BookResource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author rkc
 * @date 2020/9/17 21:34
 * @version 1.0
 */
@Mapper
public interface BookResourceMapper {

    /**
     * 查询所有资源信息
     * @return 资源信息集合
     */
    List<BookResource> selectAllResourceAlone();

    /**
     * 根据图书的id查询其所有的资源对象
     * @param bookId 图书id
     * @return 指定图书下的所有资源
     */
    List<BookResource> selectAllResourceAloneByBookId(Integer bookId);

    /**
     * 根据资源id查询资源对象
     * @param resourceId 资源id
     * @return 资源对象
     */
    BookResource selectResourceById(Integer resourceId);

    /**
     * 更新资源信息
     * @param bookResource 资源对象
     * @return 影响行数
     */
    int updateResource(BookResource bookResource);

    /**
     * 根据id删除资源
     * @param resourceId 资源id
     * @return 影响行数
     */
    int deleteResourceById(Integer resourceId);
}
