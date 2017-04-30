package com.bamboo.jercn.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Code12----------------------------------------------
 * Created by bamboo on 2017/4/30.
 */
@Entity
@Table(name = "s_role", catalog = "test")
public class Role implements Serializable {

    private Integer id;
    private String code; //角色代码
    private String name; //角色名称
    private User user;   // 角色对应的用户 (一个用户有多个角色)

    public Role(){}

    public Role(User user) {
        this.user = user;
    }

    public Role(User user, String name) {
        this.user = user;
        this.name = name;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "code", length = 20)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "name", length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
