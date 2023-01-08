package com.ygl.rege.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygl.rege.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.PathVariable;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
  @Update("update dish set status=#{status} where id=#{id} ")
  public void updateSt(@Param("id") long id, @Param("status") int status);
}
