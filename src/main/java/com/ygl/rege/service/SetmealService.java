package com.ygl.rege.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ygl.rege.commen.R;
import com.ygl.rege.dto.SetmealDto;
import com.ygl.rege.entity.Setmeal;

public interface SetmealService extends IService<Setmeal> {
  public R<String> saveWithDish(SetmealDto setmealDto);
}
