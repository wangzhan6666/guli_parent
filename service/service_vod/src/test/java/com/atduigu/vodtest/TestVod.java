package com.atduigu.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

/**
 * @Classname TestVod
 * @Description TODO
 * @Date 2020/6/3 11:29
 * @Created by wangzhan
 */
public class TestVod {
    public static void main(String[] args) throws Exception {
        // 1 根据视频id获取视频播放地址
        //TestVod.getPlayUrl();

        System.out.println("========================================");

        // 2 根据视频id获取视频播放凭证
        TestVod.getPlayAuth();

        // 3 本地文件上传
        String accessKeyId = "LTAI4G4u7MuwUNy2K2UrkG7H";
        String accessKeySecret = "GHZtBu3azDGsCCU1XJBn7KzZuyGnfK";
        String title = "test video name";      // 上传之后文件名称
        String fileName = "H:\\6 - What If I Want to Move Faster.mp4";                  // 本地文件的路径名称

        //TestVod.testUploadVideo(accessKeyId, accessKeySecret, title, fileName);
    }

    // 1 根据视频id获取视频播放地址
    public static void getPlayUrl() throws Exception {
        // 创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI4G4u7MuwUNy2K2UrkG7H", "GHZtBu3azDGsCCU1XJBn7KzZuyGnfK");

        // 创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        // 向request对象里面设置视频id
        request.setVideoId("c2b98273d7e74a5eb4aa18dbd993a0ad");


        // 调用初始化对象里面的方法，传递request，获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");

    }

    // 2 根据视频id获取视频播放凭证
    public static void getPlayAuth() throws Exception {
        // 创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI4G4u7MuwUNy2K2UrkG7H", "GHZtBu3azDGsCCU1XJBn7KzZuyGnfK");

        // 创建获取视频凭证的request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        // 向request中设置视频id
        request.setVideoId("1607445e38564460bdc6ff042883f50d");

        // 调用初始化对象的方法得到凭证
        response = client.getAcsResponse(request);

        // 播放凭证
        System.out.println("playAuth:" + response.getPlayAuth());

        //VideoMeta信息
        System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
    }

    // 3 本地文件上传
    public static void testUploadVideo(String accessKeyId, String accessKeySecret, String title, String fileName) {
        // 上传视频的方法
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);

        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

}
