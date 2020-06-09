package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-06
 */
@RestController
@RequestMapping("/eduorder/paylog")
//@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    // 1 生成微信支付二维码接口
    // 参数是订单号
    @GetMapping("creatNative/{orderNo}")
    public R creatNative(@PathVariable String orderNo) {
        // 返回信息，包含二维码地址，还有其他信息
        Map map = payLogService.createNative(orderNo);

        return R.ok().data(map);
    }

    // 2 查询订单支付状态
    // 参数： 订单号， 根据订单号查询  支付状态
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {

        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {
            return R.error().message("支付出错了");
        }
        // 如果返回map里面不为空，通过map获取订单状态
        if (map.get("trade_state").equals("SUCCESS")) {     // 支付成
            // 添加记录到支付表，更新订单表订单状态
            payLogService.updateOrdersStatus(map);
            return R.ok();
        }
        return R.ok().code(25000).message("支付中");
    }

}

