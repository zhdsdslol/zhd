package com.example.my_movies.dao;

import com.example.my_movies.models.MoviesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface movieDao extends JpaRepository<MoviesEntity,Integer>, JpaSpecificationExecutor<MoviesEntity> {
    @Query(value = "select * from movies where name like ?1   ",nativeQuery = true)
    Page<MoviesEntity> findByNameLike(String name, Pageable pageable);

    @Query(value = "select * from movies where type =?1   ",nativeQuery = true)
    Page<MoviesEntity> findByType(String type,Pageable pageable);



    @Modifying
    @Transactional
    @Query(value = "update movies set dianzan = dianzan+1 where id = ?1",nativeQuery = true)
    int updatadianzan(Integer id);
}
