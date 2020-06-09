package com.atguigu.eduservice.entity.vo;

import lombok.Data;

/**
 * @Classname CoursePublishVo
 * @Description TODO
 * @Date 2020/6/2 17:42
 * @Created by wangzhan
 */
@Data
public class CoursePublishVo {

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示

}
