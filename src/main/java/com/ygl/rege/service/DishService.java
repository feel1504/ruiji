package com.ygl.rege.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ygl.rege.commen.R;
import com.ygl.rege.dto.DishDto;
import com.ygl.rege.entity.Dish;
import com.ygl.rege.entity.DishFlavor;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface DishService extends IService<Dish> {
    public R<String> saveWithFlavor(DishDto dishDto);
    public R<DishDto> getById(long id);
    public R<String> update(DishDto dishDto);
    public R<String> delete(long[] ids);
    public R<String> updateStatus(long[] ids,int status);
}
