package com.atguigu.eduorder.client;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Classname EduClient
 * @Description TODO
 * @Date 2020/6/6 22:31
 * @Created by wangzhan
 */
@Component
@FeignClient("service-edu")
public interface EduClient {

    @PostMapping("/eduservice/course/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);
}
