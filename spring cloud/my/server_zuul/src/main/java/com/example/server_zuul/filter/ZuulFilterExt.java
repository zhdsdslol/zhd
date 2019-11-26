package com.example.server_zuul.filter;

import com.example.server_zuul.utils.RedisUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ZuulFilterExt extends ZuulFilter {
    @Autowired
    private RedisUtils redisUtils;
    /**
     * pre：路由发生之前；
       routing：路由发生时；
       post：路由发生之后；
       error：发送错误调用时。
     */
    @Override
    public String filterType() {

        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        /*RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if(request.getMethod().equals("OPTIONS")){
            return false;
        }
        return true;*/
        return true;
    }

    /**
     * 过滤器的具体逻辑
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //不拦截登录页和视频主页
        if("/users/user/userlogin".equals(request.getServletPath())||"/movies/movie/querymovie".equals(request.getServletPath())
            ||"/movies/movie/getmovie".equals(request.getServletPath())||request.getServletPath().endsWith(".mp4")||request.getServletPath().endsWith(".jpg")){
            return null;
        }

        //检查sessionid
        String key = request.getHeader("key");
        String value = request.getHeader("value");
        System.out.println(key);
        System.out.println(value);
        if(key==null||"".equals(key)||value==null||"".equals(value)){
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("{\"status\":false,\"msg\":\"请登录！\"}");
            ctx.getResponse().setContentType("application/json;charset=UTF-8");
            return null;
        }
        //sessionid存在登录信息 并且redis缓存没过期
        if(value.equals(redisUtils.get(key))){
                redisUtils.set(key,value,600);//刷新redis
                return null;
                /*request.getSession().getAttribute(cookie.getValue());*/
         }
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(401);
        ctx.setResponseBody("{\"status\":false,\"msg\":\"登录已过期请退出并重新登录！\"}");
        ctx.getResponse().setContentType("application/json;charset=UTF-8");
        return null;
    }

    /**
     * @param response 重定向
     */
    public HttpServletResponse sendRedirect(HttpServletResponse response) throws IOException {
            response.sendRedirect("/users/user/userlogin");
            return response;

    }
}
