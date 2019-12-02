package com.example.my_movies.service;

import com.example.my_movies.models.DianzanEntity;
import com.example.my_movies.models.MoviesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieServiceImpl {
    boolean AddMv(MoviesEntity movie) throws Exception;
    boolean DeleteMv(Integer id);
    boolean UpdateMv(MoviesEntity movie);
    Page<MoviesEntity> FindMv(Pageable pageable);
    Page<MoviesEntity> FindByType(String type, Pageable pageable);
    Page<MoviesEntity> FindByName(String name,Pageable pageable);
    Page<MoviesEntity> FindByTypeAndName(String type,String name,Pageable pageable);
    MoviesEntity getmovie(Integer id);
    boolean dianzan(Integer userid,Integer movieid, String movieids);
    DianzanEntity finddianzan(Integer userid);
}
