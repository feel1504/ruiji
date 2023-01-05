package com.ygl.rege.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ygl.rege.entity.Category;
import com.ygl.rege.mapper.CategoryMapper;
import com.ygl.rege.service.CategaryService;
import org.springframework.stereotype.Service;

@Service
public class CategaryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategaryService {
}
