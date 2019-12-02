package com.example.shop.service;

import com.example.shop.dao.orderDao;
import com.example.shop.model.OrdersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class orderService implements orderServiceI {
    @Autowired
    private orderDao orderDao;


    @Override
    public void save(OrdersEntity orderEntity) {
        orderDao.save(orderEntity);
    }

    @Override
    public List<OrdersEntity> findall(Integer id) {
        return orderDao.findAllByUserid(id);
    }
}
