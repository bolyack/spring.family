package com.bamboo.jercn.util;

import com.bamboo.jercn.config.SecurityUser;
import com.bamboo.jercn.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * Created by bamboo on 2017/4/30.
 */
public class AuthenUtil {

    /** 登录角色 **/
    public static final GrantedAuthority ROLE_LOGIN = new SimpleGrantedAuthority("ROLE_LOGIN");

    /**
     * 获取登陆的用户信息
     * @return
     */
    public static User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            if (authentication.getPrincipal() instanceof  UserDetails) {
                SecurityUser userDetails = (SecurityUser) authentication .getPrincipal();
                if(userDetails != null) {
                    return userDetails;
                }
            }
        }
        return null;
    }

    /**
     * 获得用户ID
     * @return
     */
    public static Integer getUserId(){
        User user = (User) getUser();
        if(user != null) {
            return user.getId();
        }
        return null;
    }
    /**
     * 重新加载session
     *
     * @param user
     */
    @SuppressWarnings("unused")
    public static void reloadSession(User user) throws Exception {
        User sessionUser = getUser();
        sessionUser  = new SecurityUser(user);
    }

    /**
     * 获得用户名
     * @return
     */
    public static String getUserName(){
        User user = getUser();
        if(user != null) {
            return user.getName();
        }
        return null;
    }


    /**
     * 是否拥有指定权限
     *
     * @param roleAuthority 指定权限
     * @return 拥有则返回<code>TRUE</code>
     */
    public static boolean isGranted(GrantedAuthority roleAuthority) {
        Collection<? extends GrantedAuthority> authorities = getAuthorities();
        return null == authorities ? false : CollectionUtils.contains(authorities.iterator(),
                roleAuthority);
    }

    /**
     * 是否登陆
     * @return
     */
    public static boolean isLogin(){
        return isGranted(ROLE_LOGIN);
    }

    /**
     * 获得拥有的角色
     * @return
     */
    public static Collection<? extends GrantedAuthority> getAuthorities(){
        return getAuthentication().getAuthorities();
    }

    /**
     * 获取认证上下文
     * <p>安全上下文持有者中若没有，则创建一个匿名角色的认证上下文</p>
     *
     * @return 认证上下文
     */
    public static Authentication getAuthentication() {
        Authentication authentication = (Authentication) SecurityContextHolder
                .getContext().getAuthentication();
        return authentication;
    }

}
