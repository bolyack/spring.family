package com.bamboo.jercn.config;

import com.bamboo.jercn.domain.Role;
import com.bamboo.jercn.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Code9----------------------------------------------
 * Created by bamboo on 2017/4/30.
 */
public class SecurityUser extends User implements UserDetails {

    private static final long serialVersionUID = 1020583198192506766L;

    public SecurityUser(User user) {
        if (null != user) {
            this.setId(user.getId());
            this.setName(user.getName());
            this.setEmail(user.getEmail());
            this.setPassword(user.getPassword());
            this.setDob(user.getDob());
            this.setRoles(user.getRoles());
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Set<Role> userRoles = this.getRoles();
        if (null != userRoles) {
            for (Role role : userRoles) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                authorities.add(authority);
            }
        }
        return authorities;
    }

    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getName();
    }

    /**
     * 是否过期(true 不过期)
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户未锁定为true
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 证书不过期为true
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
