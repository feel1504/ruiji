package com.ygl.rege.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ygl.rege.entity.Orders;
import com.ygl.rege.mapper.OrderMapper;
import com.ygl.rege.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
    public void submit(Orders orders){

    }

}
