package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname ChapterVo
 * @Description TODO    章节
 * @Date 2020/6/2 9:16
 * @Created by wangzhan
 */
@Data
public class ChapterVo {

    private String id;

    private String title;

    private List<VideoVo> children = new ArrayList<>();

}
