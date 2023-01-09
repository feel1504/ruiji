package com.ygl.rege.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygl.rege.commen.R;
import com.ygl.rege.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.PathVariable;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
    @Update("update setmeal set status=#{status} where id=#{id}")
    public void updateSt(@Param("status") int status, @Param("id") long id);
}
