package com.ygl.rege.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ygl.rege.commen.R;
import com.ygl.rege.dto.SetmealDto;
import com.ygl.rege.entity.Category;
import com.ygl.rege.entity.DishFlavor;
import com.ygl.rege.entity.Setmeal;
import com.ygl.rege.entity.SetmealDish;
import com.ygl.rege.service.CategaryService;
import com.ygl.rege.service.SetmealDishService;
import com.ygl.rege.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {
  @Autowired
  SetmealService setmealService;
  @Autowired
  SetmealDishService setmealDishService;
  @Autowired
  CategaryService categaryService;

  /**
   * 保存套餐
   * @param setmealDto
   * @return
   */
  @PostMapping
  public R<String> save(@RequestBody SetmealDto setmealDto){
    return setmealService.saveWithDish(setmealDto);
  }

  /**
   * 分页
   * @param page
   * @param pageSize
   * @param name
   * @return
   */
  @GetMapping("/page")
  public R<Page> page(int page,int pageSize,String name){
    Page<Setmeal> pageInfo = new Page<>(page,pageSize);
    Page<SetmealDto> dtoPage = new Page<>();

    LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.like(name!=null,Setmeal::getName,name);
    queryWrapper.orderByDesc(Setmeal::getUpdateTime);
    setmealService.page(pageInfo,queryWrapper);

    BeanUtils.copyProperties(pageInfo,dtoPage,"records");
    List<Setmeal> records = pageInfo.getRecords();
    List<SetmealDto> list = records.stream().map((item) -> {
      SetmealDto dto = new SetmealDto();
      BeanUtils.copyProperties(item,dto);
      long categoryId = item.getCategoryId();
      String type = categaryService.getOne(new LambdaQueryWrapper<Category>().eq(Category::getId, categoryId)).getName();
      dto.setCategoryName(type);
      return dto;
    }).collect(Collectors.toList());

    dtoPage.setRecords(list);
    return R.success(dtoPage);
  }

  @DeleteMapping
  public R<String> deleteByIds(@RequestParam List<Long> ids){
    return setmealService.deleteByIds(ids);
  };

  @GetMapping("/{id}")
  public R<SetmealDto> getById(@PathVariable Long id){
    Setmeal setmeal = setmealService.getById(id);
    LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(SetmealDish::getSetmealId,id);
    List<SetmealDish> list = setmealDishService.list(queryWrapper);

    SetmealDto dto = new SetmealDto();
    BeanUtils.copyProperties(setmeal,dto);
    dto.setSetmealDishes(list);
    return R.success(dto);
  }
  @PutMapping
  public R<String> update(@RequestBody SetmealDto setmealDto){
    return setmealService.update(setmealDto);
  }

  @PostMapping("/status/{status}")
  public R<String> updateSt(@PathVariable int status,long[] ids){
    return setmealService.updateSt(status,ids);
  }
}
