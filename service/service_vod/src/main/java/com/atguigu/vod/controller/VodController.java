package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuilException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantPropertiesUtil;
import com.atguigu.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Classname VodController
 * @Description TODO
 * @Date 2020/6/3 16:16
 * @Created by wangzhan
 */
@RestController
@RequestMapping("/eduvod/video")
//@CrossOrigin
public class VodController {

    @Autowired
    VodService vodService;

    // 1 上传视频到阿里云
    @PostMapping("uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file) {
        // 返回上传视频的id值
        String videoId = vodService.uploadVideoAly(file);
        return R.ok().data("videoId", videoId);
    }

    // 2 根据视频id删除阿里云视频
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id) {
        try {
            // 初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtil.KEY_ID, ConstantPropertiesUtil.KEY_SCRET);
            // 删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 向request设置视频id
            request.setVideoIds(id);
            // 调用初始化对象的方法实现删除
            client.getAcsResponse(request);

            return R.ok();
        }catch (Exception e) {
            e.printStackTrace();
            throw new GuilException(20001, "删除视频失败");
        }
    }

    // 3 删除多个阿里云中视频的方法
    // 参数多个视频id
    @DeleteMapping("deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreALyVideo(videoIdList);
        return R.ok();
    }

    // 4 根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            // 创建初始化对象
            DefaultAcsClient client =
                    InitVodClient.initVodClient(ConstantPropertiesUtil.KEY_ID, ConstantPropertiesUtil.KEY_SCRET);

            // 创建获取凭证request和response
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            // 向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth", playAuth);

        }catch (Exception e) {
            throw new GuilException(20001, "获取凭证失败");
        }
    }
}
