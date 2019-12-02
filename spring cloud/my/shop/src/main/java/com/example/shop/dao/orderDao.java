package com.example.shop.dao;

import com.example.shop.model.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface orderDao extends JpaRepository<OrdersEntity,Integer> {
        List<OrdersEntity> findAllByUserid(Integer id);
}
