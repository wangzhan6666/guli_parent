package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname EduLoginController
 * @Description TODO
 * @Date 2020/5/31 11:04
 * @Created by wangzhan
 */
@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin        // 解决跨域问题
public class EduLoginController {

    // login
    @PostMapping("login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    // info
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://ae01.alicdn.com/kf/Hd09b25c3afa6434fbfee5c91a7da753ah.jpg");
    }
}
