package com.ygl.rege.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ygl.rege.commen.R;
import com.ygl.rege.entity.OrderDetail;
import com.ygl.rege.entity.Orders;
import com.ygl.rege.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }

    @GetMapping("/page")
    public R<Page> page(
            int page, int pageSize,  String number,
            @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss") Date beginTime,
            @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss") Date endTime){

        Page<Orders> pageInfo = new Page<>(page, pageSize);
        // 创建查询条件对象。
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(number), Orders::getNumber, number);
        if (beginTime != null) {
            queryWrapper.between(Orders::getOrderTime, beginTime, endTime);
        }
        orderService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }
}
