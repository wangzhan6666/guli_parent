package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname TestEasyExcel
 * @Description TODO
 * @Date 2020/6/1 10:02
 * @Created by wangzhan
 */
public class TestEasyExcel {

    public static void main(String[] args) {
        // 实现excel写的操作
        // 1 设置写入文件夹地址和excel文件名称
        //String fileName = "C:\\Users\\acer\\Desktop\\write.xlsx";

        // 2 调用easyexcel里面的方法实现写操作
        // write方法两个参数，第一个参数：
       // EasyExcel.write(fileName, DemoData.class).sheet("学生列表").doWrite(getData());


        // 实现excel读的操作
        String fileName = "C:\\Users\\acer\\Desktop\\write.xlsx";

        EasyExcel.read(fileName, DemoData.class, new ExcelListener()).sheet().doRead();

    }

    // 创建方法返回list集合
    private static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("lucy" + i);
            list.add(data);
        }
        return list;
    }

}
