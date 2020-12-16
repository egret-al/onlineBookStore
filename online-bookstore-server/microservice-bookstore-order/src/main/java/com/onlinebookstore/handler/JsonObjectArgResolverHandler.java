package com.onlinebookstore.handler;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinebookstore.annotation.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 自定义JsonObject的处理类，在controller层需要json转javabean的对象上使用即可
 * @author rkc
 * @date 2020/11/7 22:15
 * @version 1.0
 */
@Slf4j
public class JsonObjectArgResolverHandler implements HandlerMethodArgumentResolver {
    private static final String JSON_REQUEST_BODY = "JSON_REQUEST_BODY";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JsonObject.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory webDataBinderFactory)
            throws Exception {
        //获取方法上的注解
        JsonObject jsonObject = parameter.getParameterAnnotation(JsonObject.class);
        //得到注解上的value
        String paramName = jsonObject.value();
        //获取参数的字节码
        Class<?> paramObjClass = parameter.getParameterType();
        String json = getRequestBody(webRequest);

        //解析json字符串为JSONObject
        JSONObject root = JSONObject.parseObject(json);
        //得到对应value名字的Object对象
        Object res = root.get(paramName);
        //因为res是Object，直接返回会报错，只需要重新转成json字符串形式，再利用parseObject通过字节码创建对象即可
        return JSONObject.parseObject(objectMapper.writeValueAsBytes(res), paramObjClass);
    }

    /**
     * 得到json字符串
     * @param webRequest webRequest
     * @return json字符串
     */
    private String getRequestBody(NativeWebRequest webRequest) {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String jsonBody = (String) servletRequest.getAttribute(JSON_REQUEST_BODY);
        if (jsonBody == null) {
            try {
                jsonBody = IOUtils.toString(servletRequest.getInputStream());
                servletRequest.setAttribute(JSON_REQUEST_BODY, jsonBody);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonBody;
    }
}
