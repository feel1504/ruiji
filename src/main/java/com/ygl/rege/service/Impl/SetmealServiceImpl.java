package com.ygl.rege.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

  @Autowired
  SetmealDishService setmealDishService;
  @Autowired
  SetmealMapper setmealMapper;

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

  /**
   * 删除和批量删除
   * @param ids
   * @return
   */
  public R<String> deleteByIds(long[] ids){
    LambdaQueryWrapper<SetmealDish> queryWrapper;
    for (long id : ids) {
      this.removeById(id);
      queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(SetmealDish::getSetmealId,id);
      setmealDishService.remove(queryWrapper);
    }
    return R.success("删除成功");
  }

  /**
   * 更新
   * @param setmealDto
   * @return
   */
  public R<String> update(SetmealDto setmealDto){
    this.updateById(setmealDto);
    //改套餐菜品表
    List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
    setmealDishService.remove(new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId,setmealDto.getId()));

    setmealDishes.forEach((item) -> {
      item.setSetmealId(setmealDto.getId());
      setmealDishService.save(item);
    });
    return R.success("修改成功");
  }
  public R<String> updateSt(int status, long[] ids){
    for (long id : ids) {
      setmealMapper.updateSt(status,id);
    }
    return R.success("修改成功");
  }
}
