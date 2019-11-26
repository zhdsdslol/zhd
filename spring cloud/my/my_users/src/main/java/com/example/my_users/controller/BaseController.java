package com.example.my_users.controller;


import com.example.my_users.models.PageUtil;
import com.example.my_users.models.ResultData;


public class BaseController {
    private ResultData resultData = new ResultData();
    PageUtil page = new PageUtil();

    protected ResultData result(boolean is,String msg,Object data){
        resultData.setMsg(msg);
        resultData.setStatus(is);
        resultData.setData(data);
        return resultData;
    }
    protected ResultData Error(Object object){
        resultData.setMsg("不好意思，出错了！请检查重试！");
        resultData.setStatus(false);
        resultData.setData(object);
        return resultData;
    }

    protected ResultData Success(Object object){
        resultData.setMsg("操作成功！");
        resultData.setStatus(true);
        resultData.setData(object);
        return resultData;
    }
    protected ResultData Success(Object object,PageUtil page){
        resultData.setMsg("操作成功！");
        resultData.setStatus(true);
        resultData.setData(object);
        return resultData;
    }

    protected boolean isnull(String data){
        if(data==null||"".equals(data)){
            return true;
        }else {
            return false;
        }
    }
    protected PageUtil getPage(int number,int pagesize,int totalElements){
        page.setNumber(number);
        page.setPageSize(pagesize);
        page.setTotalElements(totalElements);
        page.setNow();
        return page;
    }
}
