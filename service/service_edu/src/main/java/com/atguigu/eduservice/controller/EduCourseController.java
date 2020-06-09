package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.front.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQueryVo;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-01
 */
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    // 课程列表
    // TODO 完善条件查询带分页
    @PostMapping("pageCourse/{current}/{limit}")
    public R getCourseList(@PathVariable long current,
                           @PathVariable long limit,
                           @RequestBody CourseQueryVo courseQueryVo) {

        Page<EduCourse> coursePage = new Page<>(current, limit);

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        String title = courseQueryVo.getTitle();
        String status = courseQueryVo.getStatus();

        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }

        courseService.page(coursePage, wrapper);

        long total = coursePage.getTotal();

        List<EduCourse> list = coursePage.getRecords();
        return R.ok().data("total", total).data("list", list);
    }

    // 删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {

        courseService.removeCourse(courseId);

        return R.ok();
    }


    // 1 添加课程基本信息的方法
    @PostMapping("addCourserInfo")
    public R addCourserInfo(@RequestBody CourseInfoVo courseInfoVo) {
        // 返回添加之后课程id，为了后面添加课程大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);

        return R.ok().data("courseId", id);
    }

    // 2 根据课程id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {

        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);

        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    // 3 修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {

        courseService.updateCourseInfo(courseInfoVo);

        return R.ok();
    }

    // 4 根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);

        return R.ok().data("publishCourse", coursePublishVo);
    }

    // 5 课程最终发布
    // 修改课程的状态
    @GetMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");        // 设置课程发布状态
        courseService.updateById(eduCourse);

        return R.ok();
    }

    // 6 根据课程id查询课程的基本信息
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id) {

        CourseWebVo baseCoursesInfo = courseService.getBaseCoursesInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(baseCoursesInfo, courseWebVoOrder);
        return courseWebVoOrder;
    }

}

