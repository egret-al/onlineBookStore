package com.onlinebookstore.mapper;

import com.onlinebookstore.entity.bookserver.BookType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author rkc
 * @date 2020/9/30 16:10
 * @version 1.0
 */
@Mapper
public interface BookTypeMapper {

    /**
     * 查询所有类型下面的所有图书最新的count条图书
     * @return List<BookType>
     */
    List<BookType> selectAllTypeWithBook(int count);

    /**
     * 查询所有类型
     * @return 图书类型集合
     */
    List<BookType> selectAllType();

    /**
     * 根据id查询类型
     * @param id id
     * @return 类型
     */
    BookType selectTypeById(Integer id);

    /**
     * 根据类型
     * @param bookType 实体类
     * @return 影响行数
     */
    int updateType(BookType bookType);

    /**
     * 添加类型
     * @param bookType 类型实体类
     * @return 影响行数
     */
    int insertType(BookType bookType);
}
