package com.cfx.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cfx.reggie.common.R;
import com.cfx.reggie.dto.DishDto;
import com.cfx.reggie.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    //新增菜品和口味数据
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);

}
