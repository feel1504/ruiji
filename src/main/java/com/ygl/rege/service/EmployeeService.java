package com.ygl.rege.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ygl.rege.commen.R;
import com.ygl.rege.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService extends IService<Employee> {
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee);
    public R<String> logout(HttpServletRequest request);
    public R<Page> getPage(@Param("page") int page, @Param("pageSize") int pageSize,String name);
    public R<String> addEmployee(HttpServletRequest request, @RequestBody Employee employee);
}
