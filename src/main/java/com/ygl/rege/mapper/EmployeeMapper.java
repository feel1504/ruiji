package com.ygl.rege.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygl.rege.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

//继承mybatisPlus下的BaseMapper接口
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
