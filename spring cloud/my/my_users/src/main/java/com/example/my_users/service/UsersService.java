package com.example.my_users.service;

import com.example.my_users.dao.addQQBDao;
import com.example.my_users.dao.usersDao;
import com.example.my_users.models.AddqqbEntity;
import com.example.my_users.models.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;
import java.util.List;


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
        int flag =  usersDao.update(qqb.getNumber(),qqb.getUserid());
        if(flag==0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
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

    @Override
    public List<AddqqbEntity> findAllByUserid(Integer userid) {
        return QQBDao.findAllByUserid(userid);
    }

    @Override
    public int updateqqb(Integer qqb, Integer userid) {
        return usersDao.update(qqb,userid);
    }
}
