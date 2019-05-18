package com.magpie.controller;

import com.magpie.entity.Product;
import com.magpie.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductRepository repo;
    @RequestMapping("/cust/products")
    public List<Product> findAll() {
        return repo.findAll();
    }
}
