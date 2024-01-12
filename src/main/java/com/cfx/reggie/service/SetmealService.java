package com.cfx.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cfx.reggie.dto.SetmealDto;
import com.cfx.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);

    public void removeWishDish(List<Long> ids);
}
