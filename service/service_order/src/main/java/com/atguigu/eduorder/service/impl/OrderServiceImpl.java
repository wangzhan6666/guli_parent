package com.atguigu.eduorder.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.eduorder.client.EduClient;
import com.atguigu.eduorder.client.UcenterClient;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.mapper.OrderMapper;
import com.atguigu.eduorder.service.OrderService;
import com.atguigu.eduorder.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    EduClient eduClient;
    
    @Autowired
    UcenterClient ucenterClient;
    
    // 1 生成订单
    @Override
    public String createOrders(String courseId, String memberId) {

        System.out.println("courseId" + courseId);
        System.out.println("memberId" + memberId);
        // 通过远程调用，获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);

        System.out.println("userInfoOrder========"+userInfoOrder);
        // 通过远程调用，获取课程信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        System.out.println("courseInfoOrder-------"+courseInfoOrder);

        // 创建order对象，
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());   // 订单号
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);     // 订单状态
        order.setPayType(1);    // 微信支付

        baseMapper.insert(order);
        // 返回订单号
        return order.getOrderNo();
    }
}
