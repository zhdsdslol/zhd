package com.example.shop.service;

import com.example.shop.model.ShopEntity;

import java.util.List;

public interface shopServiceI {
    List<ShopEntity>  findall();
    int updateNumber(Integer id);
    ShopEntity addshop(ShopEntity shopEntity);
}
