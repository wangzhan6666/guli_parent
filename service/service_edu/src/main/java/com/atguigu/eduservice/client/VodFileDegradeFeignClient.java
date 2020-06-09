package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname VodFileDegradeFeignClient
 * @Description TODO
 * @Date 2020/6/4 9:58
 * @Created by wangzhan
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {

    // 出错之后执行
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}
