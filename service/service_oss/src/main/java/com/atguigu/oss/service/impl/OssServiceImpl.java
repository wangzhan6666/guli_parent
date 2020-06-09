package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Classname OssServiceImpl
 * @Description TODO
 * @Date 2020/5/31 22:35
 * @Created by wangzhan
 */
@Service
public class OssServiceImpl implements OssService {

    // 上传头像到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POIND;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String backetName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 获取文件名称
            String fileName = file.getOriginalFilename();
            // 1 在文件名称里面添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;

            // 2 把文件按照日期进行分类
            // 2020/6/1/01.jpg
            // 获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");

            // 拼接   2020/6/1/gaasdga01.jpg
            fileName = datePath + "/" +fileName;

            // 获取上传文件流。
            InputStream inputStream = file.getInputStream();
            // 调用oss方法实现上传
            // 第一个参数 BucketName
            // 第二个参数  上传到oss文件路径和文件名称   aa/bb/1.jpg
            // 第三个参数  上传文件输入流
            ossClient.putObject(backetName, fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            // 把上传之后文件路径返回
            // 需要把上传到阿里云oss路径手动拼接起来
            // https://edu-wangzhan.oss-cn-shenzhen.aliyuncs.com/timg.gif
            String url = "https://" + backetName + "." + endpoint + "/" + fileName;

            return url;

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
