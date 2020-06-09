package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Classname DemoData
 * @Description TODO    excel 读，写的操作类
 * @Date 2020/6/1 9:58
 * @Created by wangzhan
 */
@Data
public class DemoData {

    // 设置excel表头名称
    // index表示列
    @ExcelProperty(value = "学生编号", index = 0)
    private Integer sno;

    @ExcelProperty(value = "学生姓名", index = 1)
    private String sname;

}
