package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuilException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantPropertiesUtil;
import com.atguigu.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @Classname VodServiceImpl
 * @Description TODO
 * @Date 2020/6/3 16:17
 * @Created by wangzhan
 */
@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideoAly(MultipartFile file) {

        try {
            // fileName 上传文件原始名称
            String fileName = file.getOriginalFilename();

            // title    阿里云的显示的名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));

            // inputStream：上传文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.KEY_ID, ConstantPropertiesUtil.KEY_SCRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }

            return videoId;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeMoreALyVideo(List videoIdList) {
        try {
            // 初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtil.KEY_ID, ConstantPropertiesUtil.KEY_SCRET);
            // 删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            // videoIdList 值转换为 1,2,3
            String videoIds = StringUtils.join(videoIdList.toArray(), ",");
            // 向request社会视频id

            // 向request设置视频id
            request.setVideoIds(videoIds);
            // 调用初始化对象的方法实现删除
            client.getAcsResponse(request);

        }catch (Exception e) {
            e.printStackTrace();
            throw new GuilException(20001, "删除视频失败");
        }
    }
}
