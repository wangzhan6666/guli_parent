package com.atguigu.eduservice.controller.front;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.CommentClient;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.front.UcenterMemberVo;
import com.atguigu.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-06
 */
@RestController
@RequestMapping("/eduservice/commentfront")
//@CrossOrigin
public class CommentFrontController {

    @Autowired
    private EduCommentService commentService;

    @Autowired
    private CommentClient commentClient;

    // 获取用户的信息
    @GetMapping("getUserInfo")
    public R getUserInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        if (StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }

        UcenterMemberVo member = commentClient.getMemberInfoById(memberId);

        return R.ok().data("member", member);
    }
    // 1 添加评论
    @PostMapping("addComment")
    public R addComment(@RequestBody EduComment comment, HttpServletRequest request) {

        // 获取用户信息
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        UcenterMemberVo memberInfo = new UcenterMemberVo();
        // 判断用户id是否为空
        if (StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }

        memberInfo = commentClient.getMemberInfoById(memberId);
        System.out.println("memberInfo=============" + memberInfo);
        comment.setMemberId(memberId);
        comment.setNickname(memberInfo.getNickname());
        comment.setAvatar(memberInfo.getAvatar());
        commentService.save(comment);

        return R.ok();
    }

    // 2 根据course_id实现分页查询课程评论的方法
    @PostMapping("getCommentList/{page}/{limit}")
    public R getCommentList(@PathVariable long page, @PathVariable long limit, String courseId) {


        Page<EduComment> pageParam = new Page<>(page, limit);

        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseId)) {
            wrapper.eq("course_id", courseId);
            wrapper.orderByDesc("gmt_create");
        }

        commentService.page(pageParam, wrapper);

        List<EduComment> commentList = pageParam.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());

        return R.ok().data(map);
    }

}

