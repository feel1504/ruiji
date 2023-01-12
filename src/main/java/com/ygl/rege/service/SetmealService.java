package com.ygl.rege.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ygl.rege.commen.R;
import com.ygl.rege.dto.SetmealDto;
import com.ygl.rege.entity.Setmeal;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
  public R<String> saveWithDish(SetmealDto setmealDto);
  public R<String> deleteByIds(List<Long> ids);
  public R<String> update(SetmealDto setmealDto);
  public R<String> updateSt(int status, long[] ids);
}
