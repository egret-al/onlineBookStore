package com.onlinebookstore.service.impl;

import com.onlinebookstore.service.SubtractResidueStrategy;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * springboot中获取spring容器
 * 方式1：直接使用@Autowired注解进行注入ApplicationContext
 * 方式2；实现ApplicationContextAware接口，Spring在populateBean之后会判断是否是该接口的子类，如果是则会调用setApplicationContext方法
 * @author rkc
 * @version 1.0
 * @date 2021/1/20 11:57
 */
@Component
public class SubtractStrategyFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 根据传入的标志符，从spring中得到对应的策略进行执行
     * @param useScore 是否使用积分
     * @return 扣除余额策略
     */
    public SubtractResidueStrategy getSubtractResidueStrategy(boolean useScore) {
        if (useScore) return applicationContext.getBean(UseScoreSubtractResidueStrategy.class);
        return applicationContext.getBean(NoUseScoreSubtractResidueStrategy.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
