package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Classname OssService
 * @Description TODO
 * @Date 2020/5/31 22:35
 * @Created by wangzhan
 */
public interface OssService {
    // 上传头像到oss
    String uploadFileAvatar(MultipartFile file);
}
