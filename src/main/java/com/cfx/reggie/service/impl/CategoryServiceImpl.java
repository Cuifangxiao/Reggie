package com.cfx.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfx.reggie.common.CustomException;
import com.cfx.reggie.entity.Category;
import com.cfx.reggie.entity.Dish;
import com.cfx.reggie.entity.Setmeal;
import com.cfx.reggie.mapper.CategoryMapper;
import com.cfx.reggie.service.CategoryService;
import com.cfx.reggie.service.DishService;
import com.cfx.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    DishService dishService;

    @Autowired
    SetmealService setmealService;
    @Override
    public void remove(Long id) {
        //查询当前分类是否关联菜品
        LambdaQueryWrapper<Dish> dishQueryWarpper = new LambdaQueryWrapper();
        dishQueryWarpper.eq(Dish::getCategoryId,id);
        int count = dishService.count(dishQueryWarpper);
        if(count > 0) {
            throw new CustomException("当前关联了菜品");

        }
        //查询当前分类是否关联套餐
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);

        if(count2>0){
            throw new CustomException("当前关联了套餐");
        }

        super.removeById(id);
    }
}
