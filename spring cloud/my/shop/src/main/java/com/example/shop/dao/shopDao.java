package com.example.shop.dao;

import com.example.shop.model.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface shopDao extends JpaRepository<ShopEntity,Integer> {
    @Modifying
    @Transactional
    @Query(value = "update shop set number =number-1 where id = ?1",nativeQuery = true)
    int updateNumber(Integer id);
}
