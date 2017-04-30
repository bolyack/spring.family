package com.bamboo.jercn.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Code8----------------------------------------------
 * Created by bamboo on 2017/4/30.
 */
@Entity
@Table(name = "s_user", catalog = "test")  //code 11
public class User implements Serializable {

    private Integer id;
    private String name;
    private String email;
    private String password;
    private Date dob;
    private Set<Role> Roles = new HashSet<Role>(0);// Code12

    public User(){}

    public User(String name, String email, String password, Date dob, Set<Role> Roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.Roles = Roles;
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

    @Column(name = "name", length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "email", length = 20)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", length = 20)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "dob", length = 20)
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    public Set<Role> getRoles() {
        return this.Roles;
    }

    public void setRoles(Set<Role> Roles) {
        this.Roles = Roles;
    }
}
