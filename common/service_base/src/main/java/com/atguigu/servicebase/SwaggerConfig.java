package com.atguigu.servicebase;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Classname SwaggerConfig
 * @Description TODO    1 在guli_parent中新建一个子模块common，里面的service_base是swagger的开发
 *                      2 在需要使用swagger的模块中引入依赖，<artifactId>service_base</artifactId>
 *                      3 在需要使用swagger的模块application中，添加扫描 @ComponentScan(basePackages = {"com.atguigu"})
 *                      4 访问  http://localhost:端口号/swagger-ui.html
 * @Date 2020/5/30 11:20
 * @Created by wangzhan
 */
@Configuration  // 配置类
@EnableSwagger2 // swagger注解
public class SwaggerConfig {

    // swagger插件
    @Bean
    public Docket webApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                //.paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();

    }

    private ApiInfo webApiInfo(){

        return new ApiInfoBuilder()
                .title("网站-课程中心API文档")
                .description("本文档描述了课程中心微服务接口定义")
                .version("1.0")
                .contact(new Contact("java", "http://atguigu.com", "1123@qq.com"))
                .build();
    }

}
