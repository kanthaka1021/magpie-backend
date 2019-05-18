package com.magpie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magpie.entity.User;
import com.magpie.repository.UserRepository;

@RestController
public class UserController {
    @Autowired
    private UserRepository repo;
    @RequestMapping("/cust/users")
    public List<User> findAll() {
        return repo.findAll();
    }
}
