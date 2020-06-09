package com.atguigu.msmservice.service;

import java.util.Map;

/**
 * @Classname MsmService
 * @Description TODO
 * @Date 2020/6/5 15:03
 * @Created by wangzhan
 */
public interface MsmService {
    // 发送短信的方法
    boolean send(Map<String, Object> param, String phone);
}
