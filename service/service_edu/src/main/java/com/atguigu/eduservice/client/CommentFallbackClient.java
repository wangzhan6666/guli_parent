package com.atguigu.eduservice.client;

import com.atguigu.eduservice.entity.front.UcenterMemberVo;
import org.springframework.stereotype.Component;

/**
 * @Classname CommentFallbackClient
 * @Description TODO
 * @Date 2020/6/6 17:26
 * @Created by wangzhan
 */
@Component
public class CommentFallbackClient implements CommentClient {


    @Override
    public UcenterMemberVo getMemberInfoById(String memberId) {
        return null;
    }
}
