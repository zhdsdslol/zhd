package com.example.shop.service;

import com.example.shop.model.OrdersEntity;

import java.util.List;

public interface orderServiceI {
    void  save(OrdersEntity orderEntity);
    List<OrdersEntity> findall(Integer id);
}
