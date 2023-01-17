package com.ygl.rege.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;
import com.ygl.rege.commen.BaseContext;
import com.ygl.rege.commen.CustomException;
import com.ygl.rege.commen.R;
import com.ygl.rege.entity.AddressBook;
import com.ygl.rege.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressBook")
@Slf4j
public class AddressController {

    @Autowired
    AddressService addressService;
    //3351878643@QQ.COM

    /**
     * 展示所有地址
     * @return
     */
    @GetMapping("/list")
    public R<List<AddressBook>> list(){
        Long userId = BaseContext.getCurrentId();
        log.info("id:{}",userId);
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(userId!=null,AddressBook::getUserId, userId);
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);
        List<AddressBook> list = addressService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 新增地址
     * @param address
     * @return
     */
    @PostMapping
    public R<String> add(@RequestBody AddressBook address){
        log.info("add:{}",address.toString());
        Long userId = BaseContext.getCurrentId();
        address.setUserId(userId);
        address.setIsDefault(0);
        addressService.save(address);
        return R.success("保存成功");
    }

    /**
     * 查地址信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<AddressBook> getById(@PathVariable long id){
        log.info("id:{}",id);
        AddressBook address = addressService.getById(id);
        return R.success(address) ;
    };

    /**
     * 删除地址
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteById(long ids){
        log.info("ids:{}",ids);
        addressService.removeById(ids);
        return R.success("删除成功");
    }

    /**
     * 修改地址
     * @param address
     * @return
     */
    @PutMapping
    public R<String> updateById(@RequestBody AddressBook address){
        log.info("address{}",address.toString());
        addressService.updateById(address);
        return R.success("修改成功");
    }

    /**
     * 修改默认地址
     * @param address
     * @return
     */
    @PutMapping("/default")
    @Transactional
    public R<String> isDefault(@RequestBody AddressBook address){
        log.info("id:{}",address.toString());
        //获取为默认地址的id
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getIsDefault,1);
        AddressBook ads = addressService.getOne(queryWrapper);
        //默认地址改非默认
        LambdaUpdateWrapper<AddressBook> updateWrapper = null;
        try {
            updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(AddressBook::getId,ads.getId());
            updateWrapper.set(AddressBook::getIsDefault,0);
            addressService.update(updateWrapper);
        } catch (Exception e) {
        }finally {
            //传入id为默认地址
            updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(AddressBook::getId,address.getId());
            updateWrapper.set(AddressBook::getIsDefault,1);
            addressService.update(updateWrapper);
        }
        return R.success("修改成功");
    }

    /**
     * 查默认地址
     * @return
     */
    @GetMapping("/default")
    public R<AddressBook> getDefault(){
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        queryWrapper.eq(AddressBook::getIsDefault, 1);

        //SQL:select * from address_book where user_id = ? and is_default = 1
        AddressBook addressBook = addressService.getOne(queryWrapper);

        if (null == addressBook) {
            return R.error("没有找到该对象");
        } else {
            return R.success(addressBook);
        }
    }
}
