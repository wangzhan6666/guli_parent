package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.front.CourseFrontVo;
import com.atguigu.eduservice.entity.front.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-01
 */
public interface EduCourseService extends IService<EduCourse> {

    // 添加课程基本信息的方法
    String saveCourseInfo(CourseInfoVo courseInfoVo);
    // 2 根据课程id查询课程基本信息
    CourseInfoVo getCourseInfo(String courseId);
    // 3 修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);
    // 4 根据课程id查询课程确认信息
    CoursePublishVo publishCourseInfo(String id);
    // 删除课程
    void removeCourse(String courseId);
    // 1 条件查询带分页查询课程
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);
    // 2 课程详情的方法
    CourseWebVo getBaseCoursesInfo(String courseId);
}
