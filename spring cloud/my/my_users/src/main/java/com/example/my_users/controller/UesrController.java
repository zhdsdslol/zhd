package com.example.my_users.controller;

import com.example.my_users.Utils.RedisUtils;
import com.example.my_users.models.AddqqbEntity;
import com.example.my_users.models.ResultData;
import com.example.my_users.models.UsersEntity;
import com.example.my_users.models.user;
import com.example.my_users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UesrController extends  BaseController{
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UsersService usersService;

    @RequestMapping("/test")
    public String getmovie() {
        String response = restTemplate.getForObject("http://server-movies/movie/movietest", String.class);
        return response;
    }

    @RequestMapping("/updateuserfororder")
    public ResultData updateuserfororder(Integer qqb,Integer userid){
        int updateqqb = usersService.updateqqb(qqb, userid);
        if(updateqqb>0){
            return super.Success(null);
        }
        return  super.result(false,"扣除qqb失败",null);
    }
    /**
     * 获取收益记录
     * @param userid
     * @return
     */
    @RequestMapping("/addqqb")
    public ResultData addqqb(Integer userid){
        List<AddqqbEntity> allByUserid = usersService.findAllByUserid(userid);
        return super.Success(allByUserid);
    }

    /**
     * 验证登录信息
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/finduser")
    public ResultData finduser(HttpServletRequest request,Integer  id){
        user user = usersService.findUser(id);
        user.setSessionid(request.getHeader("value"));
        return super.Success(user);
    }
    /**
     * 更新球球币
     * @param qqb
     * @return
     */
    @RequestMapping("/updateqqb")
    public ResultData updateqqb(AddqqbEntity qqb){
        qqb.setAddtime(new Timestamp(System.currentTimeMillis()));
        int update = usersService.update(qqb);
        if(update==0){
            return super.result(false,"球球币更新失败了！",qqb);
        }else {
            return super.Success(null);
        }
    }
    //登录接口
    @RequestMapping("/userlogin")
    public Map usertest(String username, String password) {
        Map result = new HashMap();
        System.out.println(username+":"+password);
            if (!isnull(username) && !isnull(password)) {
                UsersEntity user = usersService.findUser(username, password);
                if (user != null) {
                    String sessionid = new Date().getTime()+"";
                    redisUtils.set(user.getUsername(), sessionid, 600);
                    user.setSessionid(sessionid);
                    result.put("status","success");
                    result.put("user",user);
                    return result;
                }
            }
                result.put("status","fail");
                return result;
    }
}
