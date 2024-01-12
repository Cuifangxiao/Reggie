package com.cfx.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cfx.reggie.common.R;
import com.cfx.reggie.dto.SetmealDto;
import com.cfx.reggie.entity.*;
import com.cfx.reggie.service.CategoryService;
import com.cfx.reggie.service.DishService;
import com.cfx.reggie.service.SetmealDishService;
import com.cfx.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    SetmealService setmealService;

    @Autowired
    DishService dishService;

    @Autowired
    SetmealDishService setmealDishService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Setmeal> info = new Page(page,pageSize);

        Page<SetmealDto> DtoInfo = new Page<>(page,pageSize);

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotEmpty(name),Setmeal::getName,name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(info,queryWrapper);
        BeanUtils.copyProperties(info,DtoInfo,"records");

        List<Setmeal> records = info.getRecords();
        List<SetmealDto> list = records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Category category = categoryService.getById(setmealDto.getCategoryId());
            if(category != null){
                String categoryName = category.getName();
                setmealDto.setCategoryName(category.getName());
            }
            return setmealDto;
        }).collect(Collectors.toList());

        DtoInfo.setRecords(list);

        return R.success(DtoInfo);
    }

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("添加成功");
    }


    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.removeWishDish(ids);
        return R.success("套餐删除成功");
    }

    @PostMapping("/status/{id}")
    public R<String> update(@PathVariable int id,@RequestParam List<Long> ids){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);

        List<Setmeal> setmeals = this.setmealService.list(queryWrapper);
        for (Setmeal setmeal : setmeals) {
            setmeal.setStatus(id);
        }

        boolean success = setmealService.updateBatchById(setmeals);

        if (success) {
            return R.success("数据更新成功！");
        } else {
            return R.error("数据更新失败！");
        }
    }



}
