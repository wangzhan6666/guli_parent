package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.front.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname CommentClient
 * @Description TODO
 * @Date 2020/6/6 15:47
 * @Created by wangzhan
 */
@FeignClient(name = "service-ucenter", fallback = CommentFallbackClient.class)
@Component
public interface CommentClient {

    // 根据用户id查询信息
    @GetMapping("/eduucenter/member/getMemberInfoById/{memberId}")
    public UcenterMemberVo getMemberInfoById(@PathVariable("memberId") String memberId);

}
