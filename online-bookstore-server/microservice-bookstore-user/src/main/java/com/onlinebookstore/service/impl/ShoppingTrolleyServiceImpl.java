package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.bookserver.Book;
import com.onlinebookstore.entity.userserver.ShoppingTrolley;
import com.onlinebookstore.mapper.ShoppingTrolleyMapper;
import com.onlinebookstore.service.BookService;
import com.onlinebookstore.service.ShoppingTrolleyService;
import com.onlinebookstore.util.JsonUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/25 20:19
 */
@Service
public class ShoppingTrolleyServiceImpl implements ShoppingTrolleyService {
    @Resource
    private ShoppingTrolleyMapper shoppingTrolleyMapper;

    @Resource
    private BookService bookService;

    @Resource
    private JsonUtil jsonUtil;

    @Override
    @SuppressWarnings("all")
    public CommonplaceResult selectCompleteProductByAccount(String username) {
        List<ShoppingTrolley> shoppingTrolleys = shoppingTrolleyMapper.selectProductByAccount(username);
        if (shoppingTrolleys.size() == 0) {
            return CommonplaceResult.buildSuccessNoMessage(shoppingTrolleys);
        }
        //book列表的list
        List<Integer> bookIds = new ArrayList<>(10);
        //记录shoppingTrolley与book的映射，等到图书微服务查询返回后，根据该映射，重新回填到shoppingTrolley
        Map<Integer, Integer> shoppingBookMap = new HashMap<>(10);
        for (ShoppingTrolley shoppingTrolley : shoppingTrolleys) {
            bookIds.add(shoppingTrolley.getBookId());
            shoppingBookMap.put(shoppingTrolley.getId(), shoppingTrolley.getBookId());
        }
        //如果购物车有内容则调用图书微服务进行id批量查询再回填
        CommonplaceResult bookListRes = bookService.selectBookByIds(bookIds);
        if (bookListRes.getCode() == 1) {
            List<Book> bookList = new LinkedList<Book>();
            //得到的data是一个由list包裹的linkedHashMap
            List<LinkedHashMap<String, Object>> books = (List<LinkedHashMap<String, Object>>) bookListRes.getData();
            for (LinkedHashMap<String, Object> bookLinkedHashMap : books) {
                //将LinkedHashMap转成Book对象
                Book book = null;
                try {
                    book = jsonUtil.mapToBean(bookLinkedHashMap, Book.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (book != null) bookList.add(book);
            }
            //遍历没有book的shoppingTrolley，根据shoppingBookMap的映射关系，查询bookList里面的book进行回填
            for (ShoppingTrolley shoppingTrolley : shoppingTrolleys) {
                //查询映射的bookId
                int bookId = shoppingBookMap.get(shoppingTrolley.getId());
                //根据得到的bookId查询bookList，将得到的book进行回填
                for (Book book : bookList) {
                    if (book.getId() == bookId) {
                        shoppingTrolley.setBook(book);
                        //移出当前book，避免再次遍历到，增加效率
                        bookList.remove(book);
                        break;
                    }
                }
            }
        }
        return CommonplaceResult.buildSuccessNoMessage(shoppingTrolleys);
    }

    @Override
    public CommonplaceResult selectProductByAccount(String username) {
        return CommonplaceResult.buildSuccessNoMessage(shoppingTrolleyMapper.selectProductByAccount(username));
    }

    @Override
    public CommonplaceResult getTrolleyById(int id) {
        ShoppingTrolley trolleyById = shoppingTrolleyMapper.getTrolleyById(id);
        if (ObjectUtils.isEmpty(trolleyById)) {
            return CommonplaceResult.buildErrorNoData("不存在该数据！");
        }
        return CommonplaceResult.buildSuccessNoMessage(trolleyById);
    }

    @Override
    public CommonplaceResult deleteTrolleyById(int id) {
        return shoppingTrolleyMapper.deleteTrolleyById(id) > 0 ? CommonplaceResult.buildSuccessNoData("删除成功！") : CommonplaceResult.buildErrorNoData("删除失败！");
    }

    @Override
    public CommonplaceResult insertShoppingTrolley(ShoppingTrolley shoppingTrolley) {
        if (ObjectUtils.isEmpty(shoppingTrolley)) {
            return CommonplaceResult.buildErrorNoData("非法请求！");
        }
        return shoppingTrolleyMapper.insertShoppingTrolley(shoppingTrolley) > 0 ? CommonplaceResult.buildSuccessNoData("添加成功！") :
                CommonplaceResult.buildErrorNoData("添加失败！");
    }

    @Override
    public CommonplaceResult modifyCollectCount(int id, int count) {
        return shoppingTrolleyMapper.modifyCollectCount(id, count) > 0 ? CommonplaceResult.buildSuccessNoData("修改成功！") : CommonplaceResult.buildSuccessNoData("修改失败！");
    }
}
