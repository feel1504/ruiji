package com.ygl.rege.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ygl.rege.commen.R;
import com.ygl.rege.entity.Employee;
import com.ygl.rege.mapper.EmployeeMapper;
import com.ygl.rege.service.EmployeeService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    public R<Employee> login(HttpServletRequest request,Employee employee){
        //1、给网页提过来的密码加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2、页面提交名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = this.getOne(queryWrapper);

        if(emp == null){
            return R.error("查无此人");
        }
        //3、对比密码
        if(!emp.getPassword().equals(password)){
            return R.error("密码有误");
        }

        //4、查看员工状态
        if(emp.getStatus() == 0){
            return R.error("账号已禁用");
        }

        //5、登录成功，将员工id存入session,并返回成功登录信息
        HttpSession session = request.getSession();
        session.setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
    public R<Page> getPage(@Param("page") int page, @Param("pageSize") int pageSize, String name){
        //构造分分页器
        Page pageInfo = new Page(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getName);
        page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }
    public R<String> addEmployee(HttpServletRequest request, Employee employee){
        //1、初始密码加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        long id = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(id);
        employee.setUpdateUser(id);
        this.save(employee);
        return R.success("添加成功");
    }
}
