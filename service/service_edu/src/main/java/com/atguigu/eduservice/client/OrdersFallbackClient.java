package com.atguigu.eduservice.client;

import org.springframework.stereotype.Component;

/**
 * @Classname OrdersFallbackClient
 * @Description TODO
 * @Date 2020/6/7 10:15
 * @Created by wangzhan
 */
@Component
public class OrdersFallbackClient implements OrdersClient {
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}
