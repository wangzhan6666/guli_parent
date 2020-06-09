package com.atguigu.eduorder.client;

import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Classname UcenterClient
 * @Description TODO
 * @Date 2020/6/6 22:31
 * @Created by wangzhan
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    @PostMapping("/eduucenter/member/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("id") String id);
}
