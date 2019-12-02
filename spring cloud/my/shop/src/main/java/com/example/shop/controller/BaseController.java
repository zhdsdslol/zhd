package com.example.shop.controller;

import com.example.shop.model.ResultData;


public class BaseController {
    private ResultData resultData = new ResultData();

    protected ResultData result(boolean is,String msg,Object data){
        resultData.setMsg(msg);
        resultData.setStatus(is);
        resultData.setData(data);
        return resultData;
    }

    protected ResultData Error(Object object){
        resultData.setMsg("不好意思，出错了！请重试！");
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

    protected boolean isnull(String data){
        if(data==null||"".equals(data)){
            return true;
        }else {
            return false;
        }
    }
}
