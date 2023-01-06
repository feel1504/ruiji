package com.ygl.rege.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ygl.rege.entity.DishFlavor;
import com.ygl.rege.mapper.DishFlavorMapper;
import com.ygl.rege.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImp extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
