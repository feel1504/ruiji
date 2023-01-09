package com.ygl.rege.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ygl.rege.commen.R;
import com.ygl.rege.dto.DishDto;
import com.ygl.rege.entity.Dish;
import com.ygl.rege.entity.DishFlavor;
import com.ygl.rege.mapper.DishMapper;
import com.ygl.rege.service.CategaryService;
import com.ygl.rege.service.DishFlavorService;
import com.ygl.rege.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional //开启事务
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    CategaryService categaryService;
    @Autowired
    DishFlavorService dishFlavorService;
    @Autowired
    DishMapper dishMapper;

    public R<String> saveWithFlavor(DishDto dishDto){
        this.save(dishDto);
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
        return R.success("数据保存成功");
    }
    public R<DishDto> getById(@PathVariable long id){
        //查出dish表
        LambdaQueryWrapper<Dish> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Dish::getId,id);
        Dish dish = this.getOne(queryWrapper1);

        //查dishFlavor表
        Long dishId = dish.getId();
        LambdaQueryWrapper<DishFlavor> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(DishFlavor::getDishId,dishId);
        List<DishFlavor> list = dishFlavorService.list(queryWrapper2);

        //整合到disDto对象上
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        dishDto.setFlavors(list);
        return R.success(dishDto);
    }

    public R<String> update(DishDto dishDto){
        //改dish菜单表
        this.updateById(dishDto);

        //改dishFlavor口味表
        List<DishFlavor> flavors = dishDto.getFlavors();
        dishFlavorService.remove(new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId,dishDto.getId()));

        flavors.forEach((item) -> {
            item.setDishId(dishDto.getId());
            dishFlavorService.save(item);
        });
        return R.success("修改成功");
    }

    public R<String> delete(long[] ids){
        LambdaQueryWrapper<DishFlavor> queryWrapper;
        for (long id : ids) {
            this.removeById(id);
            queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId,id);
            dishFlavorService.remove(queryWrapper);
        }
        return R.success("删除成功");
    }

    public R<String> updateStatus(long[] ids,int status){
        for (long id : ids) {
            dishMapper.updateSt(id,status);
        }
        return R.success("更新成功");
    }
}
