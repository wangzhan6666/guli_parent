package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Classname VodService
 * @Description TODO
 * @Date 2020/6/3 16:17
 * @Created by wangzhan
 */
public interface VodService {
    String uploadVideoAly(MultipartFile file);

    void removeMoreALyVideo(List videoIdList);
}
