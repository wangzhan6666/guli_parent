package com.atguigu.eduucenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.eduucenter.entity.UcenterMember;
import com.atguigu.eduucenter.entity.vo.RegisterVo;
import com.atguigu.eduucenter.entity.vo.UcenterMemberVo;
import com.atguigu.eduucenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-05
 */
@RestController
@RequestMapping("/eduucenter/member")
//@CrossOrigin
public class UcenterMemberController {


    @Autowired
    private UcenterMemberService memberService;

    // 1 登录
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember ucenterMember) {
        // 调用service方法实现登录
        // 返回token值，使用jwt生成
        String token = memberService.login(ucenterMember);
        if (token == null) {
            return R.error().message("没有数据");
        }

        return R.ok().data("token", token);
    }

    // 2 注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {

        boolean flag = memberService.register(registerVo);
        if (flag) {
            return R.ok();
        }else {
            return R.error().message("注册失败");
        }

    }
    
    // 3 根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        // 调用jwt工具类的方法，根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 查询数据库根据用户id获取用户信息
        UcenterMember member = memberService.getById(memberId);

        return R.ok().data("userInfo", member);
    }

    // 4 根据用户id查询用户信息
    @GetMapping("getMemberInfoById/{memberId}")
    public UcenterMemberVo getMemberInfoById(@PathVariable String memberId) {
        // 获取用户信息
        UcenterMember member = memberService.getById(memberId);

        UcenterMemberVo ucenterMemberVo = new UcenterMemberVo();

        BeanUtils.copyProperties(member, ucenterMemberVo);

        return ucenterMemberVo;
    }

    // 5
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {

        UcenterMember member = memberService.getById(id);
        // 把member对象里面值赋值给UcenterMemberOrder对象
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member, ucenterMemberOrder);

        return ucenterMemberOrder;
    }

    // 6 查询某一天的注册人数
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day) {
        Integer count = memberService.countRegisterDay(day);
        return R.ok().data("countRegister", count);
    }

}

