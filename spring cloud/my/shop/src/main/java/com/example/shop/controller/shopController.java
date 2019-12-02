package com.example.shop.controller;

import com.example.shop.model.OrdersEntity;
import com.example.shop.model.ResultData;
import com.example.shop.model.ShopEntity;
import com.example.shop.model.UsersEntity;
import com.example.shop.service.orderService;
import com.example.shop.service.shopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/shop")
public class shopController extends  BaseController{
    @Autowired
    private orderService orderService;
    @Autowired
    private shopService  shopService;
    @Autowired
    private RestTemplate restTemplate;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @RequestMapping("/findall")
    public ResultData findall(){
        return super.Success(shopService.findall());
    }

    @RequestMapping("/getorders")
    public ResultData getorders(Integer userid){
       return super.Success(orderService.findall(userid));
    }
    @RequestMapping("/addshop")
    public ResultData addshop(ShopEntity shopEntity){
        shopService.addshop(shopEntity);
        return super.Success(null);
    }
    @RequestMapping("/pay")
    @Transactional
    public ResultData pay(Integer userid,Integer shopid,Integer price,String name,String image){
        OrdersEntity orderEntity = new OrdersEntity();
        orderEntity.setAddtime(df.format(new Date()));
        orderEntity.setPic(image);
        orderEntity.setName(name);
        orderEntity.setUserid(userid);
        orderEntity.setPrice(price);
        try {
            shopService.updateNumber(shopid);
            orderService.save(orderEntity);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return super.Error(null);
        }
        ResultData forObject = restTemplate.getForObject("http://server-users/user/updateuserfororder?userid="+userid+"&qqb="+(0-price), ResultData.class);
        if(!forObject.isStatus()){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return forObject;
        }
        return super.Success(null);
    }

}
