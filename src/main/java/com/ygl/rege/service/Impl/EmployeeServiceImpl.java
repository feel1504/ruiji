package com.ygl.rege.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ygl.rege.entity.Employee;
import com.ygl.rege.mapper.EmployeeMapper;
import com.ygl.rege.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
