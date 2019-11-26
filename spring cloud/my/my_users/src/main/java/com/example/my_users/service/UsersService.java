package com.example.my_users.service;

import com.example.my_users.dao.addQQBDao;
import com.example.my_users.dao.usersDao;
import com.example.my_users.models.AddqqbEntity;
import com.example.my_users.models.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class UsersService implements UsersServiceImpl {
    @Autowired
    private usersDao usersDao;

    @Autowired
    private addQQBDao QQBDao;

    @Override
    public UsersEntity findUser(String username, String password) {
        return usersDao.findByNamePswd(username, password);
    }

    @Override
    @Transactional
    public int update(AddqqbEntity qqb) {
        int flag =  usersDao.update(qqb.getNumber());
        if(flag==0){
            return flag;
        }else{
            QQBDao.save(qqb);
            return flag;
        }
    }

    @Override
    public UsersEntity findUser(Integer id) {
        UsersEntity one = usersDao.getOne(id);
        return one;
    }
}
