package com.onlinebookstore.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author rkc
 * @date 2020/9/20 17:26
 * @version 1.0
 */
@Component
public class JsonUtil {

    @Resource
    private ObjectMapper objectMapper;

    /**
     * 对象转化为字符串
     * @param obj 实体类
     * @return 字符串
     * @throws Exception 转化失败
     */
    public String objectToString(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * map转化为实体类
     * @param map map
     * @param clazz 实体类的字节码
     * @return 实体类对象
     * @throws Exception 转化失败异常
     */
    @SuppressWarnings("all")
    public <T> T mapToBean(Map map, Class<T> clazz) throws Exception {
        return (T) objectMapper.readValue(this.objectToString(map), clazz);
    }

    /**
     * 实体类转换为map
     * @param obj 实体类对象
     * @return map
     * @throws Exception 转化失败
     */
    @SuppressWarnings("all")
    public Map beanToMap(Object obj) throws Exception {
        return objectMapper.readValue(this.objectToString(obj), Map.class);
    }
}
