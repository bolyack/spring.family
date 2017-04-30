package com.bamboo.jercn.repository;

import com.bamboo.jercn.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Code10----------------------------------------------
 * Created by bamboo on 2017/4/30.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.email=?1 and u.password=?2")
    User login(String email, String password);

    User findByEmailAndPassword(String email, String password);

    User findUserByEmail(String email);

    User findUserByName(String name);
}
