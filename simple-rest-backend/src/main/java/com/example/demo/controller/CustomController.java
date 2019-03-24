package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@RestController
public class CustomController {
    @Autowired
    private UserRepository repo;
    @RequestMapping("/cust/users/search")
    public List<User> search() {
        return repo.findAll();
    }
}
