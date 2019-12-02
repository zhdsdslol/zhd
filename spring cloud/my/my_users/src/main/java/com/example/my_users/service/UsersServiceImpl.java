package com.example.my_users.service;

import com.example.my_users.models.AddqqbEntity;
import com.example.my_users.models.UsersEntity;

import java.util.List;

public interface UsersServiceImpl {

    UsersEntity findUser(String username, String password);
    int update(AddqqbEntity qqb);
    UsersEntity findUser(Integer id);
    List<AddqqbEntity> findAllByUserid(Integer userid);
    int updateqqb(Integer qqb,Integer userid);
}
