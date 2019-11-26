package com.example.my_movies.service;

import com.example.my_movies.dao.movieDao;
import com.example.my_movies.models.MoviesEntity;
import com.example.my_movies.models.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService implements MovieServiceImpl {

    @Autowired
    private movieDao movieDao;

    @Override
    public boolean AddMv(MoviesEntity movie) throws Exception{
            movieDao.save(movie);
            return true;
    }

    @Override
    public boolean DeleteMv(Integer id) {
        return false;
    }

    @Override
    public boolean UpdateMv(MoviesEntity movie) {
        return false;
    }

    @Override
    public Page<MoviesEntity> FindMv(Pageable pageable) {
        Page<MoviesEntity> all = movieDao.findAll(pageable);
        return all;
    }

    @Override
    public Page<MoviesEntity> FindByType(String type, Pageable pageable) {
        return movieDao.findByType(type,pageable);
    }

    @Override
    public Page<MoviesEntity> FindByName(String name,Pageable pageable) {
        return movieDao.findByNameLike(name,pageable);
    }

    @Override
    public Page<MoviesEntity> FindByTypeAndName(String type, String name,Pageable pageable) {
        Specification<MoviesEntity> specification = (Specification<MoviesEntity>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            // 第一个type为MoviesEntity中的字段，第二个type为参数
            Predicate p1 = criteriaBuilder.equal(root.get("type"),type);
            list.add(p1);
            // 此处为查询name中含有name的数据
            Predicate p2 = criteriaBuilder.like(root.get("name"),"%"+name+"%" );
            list.add(p2);
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        return movieDao.findAll(specification,pageable);
    }

    @Override
    public MoviesEntity getmovie(Integer id) {
        return movieDao.getOne(id);
    }
}
