package com.ygl.rege.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ygl.rege.commen.R;
import com.ygl.rege.dto.DishDto;
import com.ygl.rege.entity.Category;
import com.ygl.rege.entity.Dish;
import com.ygl.rege.entity.DishFlavor;
import com.ygl.rege.service.CategaryService;
import com.ygl.rege.service.DishFlavorService;
import com.ygl.rege.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    DishFlavorService dishFlavorService;
    @Autowired
    DishService dishService;
    @Autowired
    CategaryService categaryService;
    @Autowired
    StringRedisTemplate redisTemplate;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        return dishService.saveWithFlavor(dishDto);
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Dish::getName,name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo,queryWrapper);

        BeanUtils.copyProperties(pageInfo,dtoPage,"records");

        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();
            String type = categaryService.getOne(new LambdaQueryWrapper<Category>().eq(Category::getId, categoryId)).getName();
            dishDto.setCategoryName(type);
            return dishDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable long id){
        return dishService.getById(id);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        return dishService.update(dishDto);
    }

    @DeleteMapping
    public R<String> deletes(long[] ids){
       return dishService.delete(ids);
    }


    @PostMapping("/status/{status}")
    public R<String> sales(@PathVariable int status, long[] ids){
        return dishService.updateStatus(ids,status);
    }

//    /dish/list?name=就看到你
//    /dish/list?categoryId=1413384954989060097

    @GetMapping("/list")
    public R<List<DishDto>> getList(Dish dish){
        List<DishDto> dtoList = null;
        //构造rediskey
        String key = "dish_"+dish.getCategoryId()+"_"+dish.getStatus();
        //从redis查菜品数据
        String s = redisTemplate.opsForValue().get(key);
        if(s != null){
            dtoList = (List<DishDto>)JSON.parse(s);
            return R.success(dtoList);
        }

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        List<Dish> list = dishService.list(queryWrapper);

        dtoList = list.stream().map((item) -> {
            DishDto dto = new DishDto();
            BeanUtils.copyProperties(item, dto);
            Long id = item.getId();
            LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(DishFlavor::getDishId, id);
            List<DishFlavor> flavors = dishFlavorService.list(wrapper);
            dto.setFlavors(flavors);
            return dto;
        }).collect(Collectors.toList());
        //将从数据库查出来的菜品数据放入redis
        redisTemplate.opsForValue().set(key,JSON.toJSONString(dtoList),1L, TimeUnit.HOURS);
        return R.success(dtoList);
    }
}
