package com.onlinebookstore.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSON;
import com.onlinebookstore.common.CommonplaceResult;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义sentinel降级异常数据，避免出现Blocked by Sentinel (flow limiting)不友好信息
 * @author rkc
 * @version 1.0
 * @date 2020/12/16 10:23
 */
@Component
public class SentinelBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        CommonplaceResult result = new CommonplaceResult();
        if (e instanceof FlowException) {
            result.setCode(0);
            result.setMessage("user-server限流异常");
        } else if (e instanceof DegradeException) {
            result.setCode(0);
            result.setMessage("user-server降级异常");
        } else if (e instanceof ParamFlowException) {
            result.setCode(0);
            result.setMessage("user-server热点参数异常");
        } else if (e instanceof SystemBlockException) {
            result.setCode(0);
            result.setMessage("user-server系统负载异常");
        } else if (e instanceof AuthorityException) {
            result.setCode(0);
            result.setMessage("user-server授权异常");
        }
        //设置json返回
        response.setStatus(200);
        response.setHeader("content-type", "application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
