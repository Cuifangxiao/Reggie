package com.cfx.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cfx.reggie.entity.Category;
import org.springframework.stereotype.Service;


public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}