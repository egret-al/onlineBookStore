package com.onlinebookstore.handler;

import com.onlinebookstore.common.CommonplaceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author rkc
 * @version 1.0
 * @date 2020/9/11 16:35
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public CommonplaceResult handle(Exception e) {
        log.error("发生异常：" + e.getMessage());
        return CommonplaceResult.buildErrorNoData(e.getMessage());
    }
}
