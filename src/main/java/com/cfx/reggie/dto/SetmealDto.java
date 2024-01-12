package com.cfx.reggie.dto;

import com.cfx.reggie.entity.Setmeal;
import com.cfx.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}