package com.atguigu.eduucenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.commonutils.R;
import com.atguigu.eduucenter.entity.UcenterMember;
import com.atguigu.eduucenter.entity.vo.RegisterVo;
import com.atguigu.eduucenter.mapper.UcenterMemberMapper;
import com.atguigu.eduucenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.GuilException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-05
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 1 登录
    @Override
    public String login(UcenterMember ucenterMember) {

        // 获取登录手机号和密码
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();

        // 手机号和密码费控判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuilException(20001, "登录失败");
        }

        // 判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember member = baseMapper.selectOne(wrapper);
        // 判断查询对象是否为空
        if (member == null) {
            throw new GuilException(20001, "登录失败");
        }

        // 判断密码
        // 因为存储在数据库密码是加密的
        // 把输入的密码进行加密，再和数据库密码进行比较
        // 加密方式 MD5
        if (!MD5.encrypt(password).equals(member.getPassword())) {
            throw new GuilException(20001, "登录失败");
        }
        // 判断用户是否禁用
        if (member.getIsDisabled()) {
            throw new GuilException(20001, "登录失败");
        }

        // 登录成功
        // 生成token字符串，使用jwt工具类
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());

        return token;
    }

    // 2 注册
    @Override
    public boolean register(RegisterVo registerVo) {
        // 获取注册的数据
        String code = registerVo.getCode(); // 验证码
        String mobile = registerVo.getMobile(); // 手机号
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); // 密码

        // 非空判断
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile)
            || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)) {
            throw new GuilException(20001, "注册失败");
        }

        // 判断验证码
        // 获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new GuilException(20001, "注册失败");
        }

        // 判断手机号是否重复,表里面存在相同手机号不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);

        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new GuilException(20001, "注册失败");
        }

        // 数据库添加
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);    // 用户不禁用
        member.setAvatar("https://edu-wangzhan.oss-cn-shenzhen.aliyuncs.com/2020/06/01/49788a2bf946443e959998bca568cfd4file.png");

        int insert = baseMapper.insert(member);

        return insert > 0;
    }

    // 根据openid判断
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);

        UcenterMember member = baseMapper.selectOne(wrapper);

        return member;
    }

    // 6 查询某一天的注册人数
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
