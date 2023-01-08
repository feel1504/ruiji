package com.ygl.rege.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ygl.rege.commen.R;
import com.ygl.rege.dto.SetmealDto;
import com.ygl.rege.entity.Setmeal;
import com.ygl.rege.entity.SetmealDish;
import com.ygl.rege.mapper.SetmealMapper;
import com.ygl.rege.service.SetmealDishService;
import com.ygl.rege.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

  @Autowired
  SetmealDishService setmealDishService;

  public R<String> saveWithDish(SetmealDto setmealDto){
    //保存setmeal
    this.save(setmealDto);

    //保存setmealDish
    List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
    setmealDishes = setmealDishes.stream().map((item) -> {
      item.setSetmealId(setmealDto.getId());
      return item;
    }).collect(Collectors.toList());

    setmealDishService.saveBatch(setmealDishes);
    return R.success("保存成功");
  }
}
