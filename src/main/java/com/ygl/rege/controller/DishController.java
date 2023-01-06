package com.ygl.rege.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ygl.rege.commen.R;
import com.ygl.rege.dto.DishDto;
import com.ygl.rege.entity.Category;
import com.ygl.rege.entity.Dish;
import com.ygl.rege.service.CategaryService;
import com.ygl.rege.service.DishFlavorService;
import com.ygl.rege.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
}
