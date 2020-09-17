package com.onlinebookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * RedisAutoConfiguration类帮助我们配置了RedisTemplate，
 * 因此可以直接注入使用
 * SpringBoot自动帮我们在容器中生成了一个RedisTemplate和一个StringRedisTemplate。但是，这个RedisTemplate的泛型是<Object,Object>，
 * 写代码不方便，需要写好多类型转换的代码；我们需要一个泛型为<String,Object>形式的RedisTemplate。并且，这个RedisTemplate没有设置数
 * 据存在Redis时，key及value的序列化方式。
 * 因为源码中RedisAutoConfiguration添加了@ConditionalOnMissingBean注解，因此我们需要重新配置RedisTemplate，这样自动化配置的
 * RedisTemplate就不会生效
 * @author rkc
 * @date 2020/9/17 14:55
 * @version 1.0
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        //配置序列化方式: Key序列化为String，Value序列化为JSON（默认使用Jackson）
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        return redisTemplate;
    }
}
