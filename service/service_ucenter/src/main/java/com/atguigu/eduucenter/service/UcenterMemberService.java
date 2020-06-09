package com.atguigu.eduucenter.service;

import com.atguigu.eduucenter.entity.UcenterMember;
import com.atguigu.eduucenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-05
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    // 1 登录
    String login(UcenterMember ucenterMember);

    // 2 注册
    boolean register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);
    // 6 查询某一天的注册人数
    Integer countRegisterDay(String day);
}
