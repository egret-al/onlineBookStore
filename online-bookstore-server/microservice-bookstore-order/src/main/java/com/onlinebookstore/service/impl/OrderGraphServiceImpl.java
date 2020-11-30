package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.orderserver.Graph;
import com.onlinebookstore.entity.orderserver.OrderAnalysisItem;
import com.onlinebookstore.entity.orderserver.SeriesItem;
import com.onlinebookstore.mapper.OrderGraphMapper;
import com.onlinebookstore.service.OrderGraphService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/29 10:17
 */
@Slf4j
@Service
public class OrderGraphServiceImpl implements OrderGraphService {
    @Resource
    private OrderGraphMapper orderGraphMapper;

    private static final Integer YEAR = 12;
    private static final Integer HALF_YEAR = 6;
    private static final String COUNT_LIST = "count_list";
    private static final String PRICE_LIST = "price_list";

    /**
     * 得到销售量最好的6个的最新半年的销售额
     * @return CommonplaceResult
     */
    @Override
    public CommonplaceResult getPriceOverviewHalfYearTop6() {
        //得到当月和前5个月的日期时间，作为横轴
        List<String> recentTime = getRecentTime("yyyy-MM", HALF_YEAR);
        //得到销售额前6的图书
        List<Integer> topIdList = orderGraphMapper.getTopPriceBookName(HALF_YEAR);
        return getCommonplaceResult(recentTime, topIdList, PRICE_LIST);
    }

    /**
     * 得到销售量最好的6个的最新半年的销售量
     * @return CommonplaceResult
     */
    @Override
    public CommonplaceResult getCountOverviewHalfYearTop6() {
        List<String> recentTime = getRecentTime("yyyy-MM", HALF_YEAR);
        List<Integer> topIdList = orderGraphMapper.getTopCountBookName(HALF_YEAR);
        return getCommonplaceResult(recentTime, topIdList, COUNT_LIST);
    }

    private CommonplaceResult getCommonplaceResult(List<String> recentTime, List<Integer> topIdList, String priceList) {
        List<SeriesItem> seriesItems = new ArrayList<>(10);
        for (Integer id : topIdList) {
            //通过bookId查询数据并封装到seriesItem中
            SeriesItem seriesItem = new SeriesItem();
            List<OrderAnalysisItem> orderAnalysisItems = orderGraphMapper.selectOrderItemByBookId(id, HALF_YEAR);
            seriesItem.setName(orderAnalysisItems.get(0).getBookName());
            seriesItem.setData(handData(orderAnalysisItems, HALF_YEAR).get(priceList));
            seriesItems.add(seriesItem);
        }
        return CommonplaceResult.buildSuccessNoMessage(new Graph.GraphBuilder().setXData(recentTime).setSeries(seriesItems).build());
    }
    /**
     * 得到从当前时间开始前beforeMonth个月的日期
     * @param format 格式化字符串
     * @param beforeMonth 前n个月
     * @return 按照format的格式返回的日期数据
     */
    public static List<String> getRecentTime(String format, int beforeMonth) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -(beforeMonth - 1));
        List<String> times = new ArrayList<>(10);
        times.add(simpleDateFormat.format(calendar.getTime()));
        for (int i = 1; i < beforeMonth; i++) {
            calendar.add(Calendar.MONTH, 1);
            times.add(simpleDateFormat.format(calendar.getTime()));
        }
        return times;
    }

    /**
     * 用于数据清理，对于没有对应数据的位置进行取0操作
     * @param orderAnalysisItems orderAnalysisItem集合，被清洗的数据
     * @param month month月前的数据进行对比，没有的进行取0
     * @return map集合，通过COUNT_LIST，PRICE_LIST两个常量值获取对应的封装数据
     */
    private Map<String, List<Integer>> handData(List<OrderAnalysisItem> orderAnalysisItems, int month) {
        Map<String, List<Integer>> res = new HashMap<>(10);
        List<Integer> priceList = new ArrayList<>(10);
        List<Integer> countList = new ArrayList<>(10);
        res.put(PRICE_LIST, priceList);
        res.put(COUNT_LIST, countList);

        //需要填充数据的月份
        List<String> recentTime = getRecentTime("yyyy-MM", month);
        Map<String, Integer> priceMap = new HashMap<>(8);
        Map<String, Integer> countMap = new HashMap<>(8);
        for (OrderAnalysisItem orderAnalysisItem : orderAnalysisItems) {
            priceMap.put(orderAnalysisItem.getDataDate(), orderAnalysisItem.getTotalPrice());
            countMap.put(orderAnalysisItem.getDataDate(), orderAnalysisItem.getTotalCount());
        }
        for (String date : recentTime) {
            if (!priceMap.containsKey(date)) {
                priceMap.put(date, 0);
            }
            if (!countMap.containsKey(date)) {
                countMap.put(date, 0);
            }
        }
        priceMap = sortMapByKey(priceMap);
        countMap = sortMapByKey(countMap);
        priceList.addAll(priceMap.values());
        countList.addAll(countMap.values());
        return res;
    }

    /**
     * 使用 Map按key进行排序
     * @param map 待排序的map
     * @return key升序的map
     */
    public static Map<String, Integer> sortMapByKey(Map<String, Integer> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Integer> sortMap = new TreeMap<String, Integer>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        sortMap.putAll(map);
        return sortMap;
    }
}
