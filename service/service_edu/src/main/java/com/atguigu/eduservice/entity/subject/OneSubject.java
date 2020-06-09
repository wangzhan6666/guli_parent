package com.atguigu.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname OneSubject
 * @Description TODO    一级分类
 * @Date 2020/6/1 13:52
 * @Created by wangzhan
 */
@Data
public class OneSubject {

    private String id;

    private String title;

    // 一个一级分类有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
