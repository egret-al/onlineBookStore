import com.onlinebookstore.OrderServerApplication;
import com.onlinebookstore.entity.orderserver.Order;
import com.onlinebookstore.mapper.OrderAnalysisMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author rkc
 * @date 2020/11/7 18:04
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderServerApplication.class)
public class OrderAnalysisTest {

    @Resource
    private OrderAnalysisMapper orderAnalysisMapper;

    @Test
    public void testGetPriceMonthly() {
        List<String> date = orderAnalysisMapper.getOrderSuccessDate();
        Map<String, Map<String, Integer>> graph = new ConcurrentHashMap<>(10);

        for (String dateItem : date) {
            List<Order> orders = orderAnalysisMapper.getOrdersByMonthly(dateItem);
            Map<String, Integer> item = new ConcurrentHashMap<>(10);
            for (Order order : orders) {
                if (item.containsKey(order.getBookName())) {
                    item.put(order.getBookName(), item.get(order.getBookName()) + order.getWholePrice());
                } else {
                    item.put(order.getBookName(), order.getWholePrice());
                }
            }
            graph.put(dateItem, item);
        }

        for (String dateItem : graph.keySet()) {
            System.out.println("时间：" + dateItem);
            Map<String, Integer> item = graph.get(dateItem);
            for (String bookName : item.keySet()) {
                System.out.println(bookName + " 销售额：" + item.get(bookName));
            }
        }
    }

    @Test
    public void testGetCountMonthly() {
        List<String> date = orderAnalysisMapper.getOrderSuccessDate();
        Map<String, Map<String, Integer>> graph = new ConcurrentHashMap<>(10);

        for (String dateItem : date) {
            List<Order> orders = orderAnalysisMapper.getOrdersByMonthly(dateItem);
            Map<String, Integer> item = new ConcurrentHashMap<>(10);
            for (Order order : orders) {
                if (item.containsKey(order.getBookName())) {
                    item.put(order.getBookName(), item.get(order.getBookName()) + order.getProductCount());
                } else {
                    item.put(order.getBookName(), order.getProductCount());
                }
            }
            graph.put(dateItem, item);
        }

        for (String dateItem : graph.keySet()) {
            System.out.println("时间：" + dateItem);
            Map<String, Integer> item = graph.get(dateItem);
            for (String bookName : item.keySet()) {
                System.out.println(bookName + " 销售量：" + item.get(bookName));
            }
        }
    }
}
