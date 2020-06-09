package com.atguigu.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname GuilException
 * @Description TODO
 * @Date 2020/5/30 14:06
 * @Created by wangzhan
 */
@Data
@AllArgsConstructor     // 生成有参数的构造方法
@NoArgsConstructor      // 生成无参数构造方法
public class GuilException extends RuntimeException {

    private Integer code;   // 状态码

    private String msg;     // 异常信息

}
