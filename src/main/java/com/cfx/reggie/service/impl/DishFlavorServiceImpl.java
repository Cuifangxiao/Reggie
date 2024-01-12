package com.cfx.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfx.reggie.entity.DishFlavor;
import com.cfx.reggie.mapper.DishFlavorMapper;
import com.cfx.reggie.service.DIshFlavorService;
import com.cfx.reggie.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DIshFlavorService {
}
