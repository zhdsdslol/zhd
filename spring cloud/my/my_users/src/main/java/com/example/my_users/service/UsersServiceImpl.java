package com.example.my_users.service;

import com.example.my_users.models.AddqqbEntity;
import com.example.my_users.models.UsersEntity;

public interface UsersServiceImpl {

    UsersEntity findUser(String username, String password);
    int update(AddqqbEntity qqb);
    UsersEntity findUser(Integer id);
}
