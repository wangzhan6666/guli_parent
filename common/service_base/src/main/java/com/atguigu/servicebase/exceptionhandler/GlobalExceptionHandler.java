package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Classname GlobalExceptionHandler
 * @Description TODO    全局的异常处理
 * @Date 2020/5/30 13:50
 * @Created by wangzhan
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 1 全局异常处理
    // 指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody   // 为了返回数据
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }

    // 2 特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody   // 为了返回数据
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理");
    }

    // 3 自定义异常处理
    @ExceptionHandler(GuilException.class)
    @ResponseBody   // 为了返回数据
    public R error(GuilException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }

}
