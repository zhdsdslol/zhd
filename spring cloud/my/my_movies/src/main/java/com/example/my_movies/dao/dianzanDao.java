package com.example.my_movies.dao;

import com.example.my_movies.models.DianzanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface dianzanDao extends JpaRepository<DianzanEntity,Integer> {
    @Modifying
    @Transactional
    @Query(value = "update dianzan set movieid=CONCAT(movieid,?1 ,',') where userid = ?2 ",nativeQuery = true)
    int update(String movieid,Integer userid);
}
