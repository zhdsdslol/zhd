package com.example.shop.service;

import com.example.shop.dao.shopDao;
import com.example.shop.model.ShopEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class shopService implements shopServiceI {

    @Autowired
    private shopDao shopDao;


    @Override
    public List<ShopEntity> findall() {
        return shopDao.findAll();
    }

    @Override
    public int updateNumber(Integer id) {
        return shopDao.updateNumber(id);
    }

    @Override
    public ShopEntity addshop(ShopEntity shopEntity) {
        return shopDao.save(shopEntity);
    }
}
