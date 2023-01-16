package com.ygl.rege.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygl.rege.controller.AddressController;
import com.ygl.rege.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper extends BaseMapper<AddressBook> {
}
