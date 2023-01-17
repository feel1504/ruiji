package com.ygl.rege.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ygl.rege.entity.Orders;

public interface OrderService extends IService<Orders> {
    public void submit(Orders orders);
}
