package com.atguigu.staservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Classname UcenterClient
 * @Description TODO
 * @Date 2020/6/7 11:01
 * @Created by wangzhan
 */
@FeignClient("service-ucenter")
@Component
public interface UcenterClient {

    @GetMapping("/eduucenter/member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
