package com.example.my_users.dao;

import com.example.my_users.models.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface usersDao extends JpaRepository<UsersEntity, Integer> {
    @Query(nativeQuery = true,value = " select * from users where username = ?1 and password = ?2 ")
    UsersEntity findByNamePswd(String username, String password);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = " update users set qqb = qqb+?1 ")
    int update(Integer qqb);
}
