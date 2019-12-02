package com.example.my_users.dao;

import com.example.my_users.models.AddqqbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface addQQBDao extends JpaRepository<AddqqbEntity,Integer> {
    List<AddqqbEntity> findAllByUserid(Integer userid);
}
