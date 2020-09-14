package com.onlinebookstore.filter;

import com.onlinebookstore.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 用户登录鉴权
 * 网关统一拦截验证，如果访问需要登录的资源而没有，在head中携带token，
 * 则认为没有登录，如果在head中token验证失败，则由网关进行拒绝，说明
 * token被伪造，拒绝访问
 * @author rkc
 * @date 2020/9/14 8:47
 * @version 1.0
 */
@Slf4j
@Component
public class GatewayAuthorityFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        URI uri = request.getURI();
        log.info("路径：" + uri + "\t" + uri.getPath());
        if (uri.getPath().contains("pri")) {
            //得到请求头
            HttpHeaders headers = request.getHeaders();
            //获取请求头中的令牌
            String token = headers.getFirst("token");
            if (StringUtils.isEmpty(token)) {
                //响应中放入返回的状态码，告知没有权限访问
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            //如果请求头中有令牌则解析令牌
            try {
                //解析令牌
                JwtUtil.parseJwt(token);
            } catch (Exception e) {
                e.printStackTrace();
                //解析token出错，说明令牌过期或者伪造等不合法情况出现
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                //返回
                return response.setComplete();
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
