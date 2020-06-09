package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

/**
 * @Classname VideoVo
 * @Description TODO    小节
 * @Date 2020/6/2 9:17
 * @Created by wangzhan
 */
@Data
public class VideoVo {

    private String id;

    private String title;

    private String videoSourceId;  // 视频id
}
