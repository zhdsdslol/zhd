package com.example.my_movies.models;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {

    /**
     每页大小
     */
    private int pageSize;


    /**
     当前页为第几页
     */
    private int number;


    /**
     总共有多少页
     */
    private int totalPages;


    /**
     总共有多少条数据
     */
    private int totalElements;

    //第几页开始查
    private  int now;

    public int getNow() {
        return now;
    }

    public void setNow() {
        this.now=(this.number-1)*this.pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }


    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public int getNumber() {
        return number;
    }


    public void setNumber(int number) {
        this.number = number;
    }


    public int getTotalPages() {
        return totalPages;
    }


    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }


    public int getTotalElements() {
        return totalElements;
    }


    public void setTotalElements(int totalElements) {
        this.totalPages = totalElements % pageSize == 0 ? totalElements/pageSize : (totalElements/pageSize) + 1;
        if(this.number>=this.totalPages){
            this.number=this.totalPages;
        }
        this.totalElements = totalElements;
    }

}

