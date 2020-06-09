package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname IndexFrontController
 * @Description TODO
 * @Date 2020/6/5 11:32
 * @Created by wangzhan
 */
@RestController
@RequestMapping("/eduservice/indexfront")
//@CrossOrigin
public class IndexFrontController {

    @Autowired
    EduCourseService courseService;

    @Autowired
    EduTeacherService teacherService;

    // 1 查询前8条热门课程，查询前4条名师
    @GetMapping("index")
    public R index() {

        // 查询前8条热门课程
        QueryWrapper<EduCourse> wrapperCourse = new QueryWrapper<>();
        wrapperCourse.orderByDesc("id");
        wrapperCourse.last("limit 8");

        List<EduCourse> courseList = courseService.list(wrapperCourse);

        // 查询前4条名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");

        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);

        return R.ok().data("courseList", courseList).data("teacherList", teacherList);
    }


}
