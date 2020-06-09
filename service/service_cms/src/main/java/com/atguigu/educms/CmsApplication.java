package com.atguigu.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Classname CmsApplication
 * @Description TODO
 * @Date 2020/6/5 11:05
 * @Created by wangzhan
 */
@SpringBootApplication
@ComponentScan("com.atguigu")       // 指定扫描位置
@MapperScan("com.atguigu.educms.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
