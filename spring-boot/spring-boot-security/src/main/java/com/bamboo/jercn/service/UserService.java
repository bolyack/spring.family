package com.bamboo.jercn.service;

import com.bamboo.jercn.domain.User;
import com.bamboo.jercn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Code7----------------------------------------------
 * Created by bamboo on 2017/4/30.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository; //code 10

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User findUserById(int id) {
        return userRepository.findOne(id);
    }

    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(int id) {
        userRepository.delete(id);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User findUserByName(String userName) {
        return userRepository.findUserByName(userName);
    }

}
