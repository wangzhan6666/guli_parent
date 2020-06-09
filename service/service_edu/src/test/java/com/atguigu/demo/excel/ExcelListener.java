package com.atguigu.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @Classname ExcelListener
 * @Description TODO
 * @Date 2020/6/1 10:15
 * @Created by wangzhan
 */
public class ExcelListener extends AnalysisEventListener<DemoData> {

    // 一行一行读取excel内容
    @Override
    public void invoke(DemoData data, AnalysisContext analysisContext) {
        System.out.println("***************" + data);
    }

    // 读取表头内容
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头：" + headMap);
    }

    // 读取完成之后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}
