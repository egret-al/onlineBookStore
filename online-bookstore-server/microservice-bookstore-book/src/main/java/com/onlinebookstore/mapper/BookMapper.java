package com.onlinebookstore.mapper;

import com.onlinebookstore.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 由于该微服务前端访问几乎都是为了获取资源，因此该持久层接口默认直接
 * 映射一对多的信息到返回的结果集
 * @author rkc
 * @date 2020/9/17 21:33
 * @version 1.0
 */
@Mapper
public interface BookMapper {

    List<Book> selectAll();

    Book selectBookById(Integer id);


}
