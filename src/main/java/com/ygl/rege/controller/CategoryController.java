package com.ygl.rege.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ygl.rege.commen.R;
import com.ygl.rege.entity.Category;
import com.ygl.rege.service.CategaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 菜品分类
 */
@RestController
@ResponseBody
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategaryService categaryService;

    /**
     * 获取所有
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        //创建分页对象
        Page<Category> pageInfo = new Page<>(page,pageSize);
        //创建条件对象
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Category::getSort);
        categaryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    @PostMapping
    public R<String> save(@RequestBody Category category){
        categaryService.save(category);
        return R.success("保存成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        categaryService.updateById(category);
        return R.success("修改成功");
    }

    @DeleteMapping
    public R<String> delete(Long ids){
        categaryService.removeById(ids);
        return R.success("删除成功");
    }
}
