package com.bamboo.jercn.config;

import com.bamboo.jercn.domain.Role;
import com.bamboo.jercn.domain.User;
import com.bamboo.jercn.service.UserService;
import com.bamboo.jercn.util.AuthenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * code1----------------------------------------------
 * 步骤3： 配置自定义Servcie
 * Created by bamboo on 2017/4/30.
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired   //数据库服务类
    private UserService userService;  // code 7

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        logger.info("登陆的用户名: " + userName);
        //User对应数据库中的用户表，是最终存储用户和密码的表，可自定义
        //本例使用User中的email作为用户名:
        User user = userService.findUserByName(userName); //User实体 code 8
        if (null == user) {
            throw new UsernameNotFoundException("UserName " + userName + " not found");
        }

        user.setRoles(getRoles(user.getId()));

        // SecurityUser实现UserDetails并将SUser的Email映射为username
        return new SecurityUser(user);  //code 9
    }

    /**
     * 根据用户获取该用户拥有的角色
     * @param userId
     * @return
     */
    private Set<Role> getRoles(Integer userId) {
        Set<Role> roles = new HashSet<Role>();
        //把用户对应的所有角色都添加到roles; 调用service接口
//        roles.addAll(roleApiService.getByUserId(userId));

        //假若没有其他角色，这里为当前通过验证的用户-默认添加登录的角色
        Role roleLogin = new Role();
        roleLogin.setCode(AuthenUtil.ROLE_LOGIN.getAuthority());
        roleLogin.setName("登陆角色");

        roles.add(roleLogin);
        return roles;
    }

}
