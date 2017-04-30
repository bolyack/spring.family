package com.bamboo.jercn.controller;

import com.bamboo.jercn.domain.User;
import com.bamboo.jercn.util.AuthenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by bamboo on 2017/4/30.
 */
@Controller
public class GreetingController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping("/")
    public String root() {
        //如不进行此项配置，从login登录成功后，会提示找不网页
        return "index";
    }

   @RequestMapping("/login")
    public String login(HttpServletRequest req, HttpServletResponse resp) {
       System.out.println("进入登录页");
       //已经成功登陆的用户，直接跳转到主页
       User user = AuthenUtil.getUser();
       if (null != user) {
           return "hello";
       }
       return "login";
    }

   /*  @RequestMapping(value = "login", method = RequestMethod.POST)
    public String loginData(HttpServletRequest request, String username, String password, Boolean rememberMe) {
        System.out.println("登陆操作 : " + username + ",," + password + ",,,," + rememberMe);

        return "hello";
    }*/

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/hello")
    public String hello() {
        SecurityContext ctx   =   SecurityContextHolder.getContext();
        Authentication auth   =   ctx.getAuthentication();
        if(auth.getPrincipal()  instanceof UserDetails) {
            User user   =   (User)auth.getPrincipal();
            System.out.println("这里是hello界面:::" + user.getEmail());
        }
        //本段代码演示如何获取登录的用户资料
        return "hello";

    }



}
