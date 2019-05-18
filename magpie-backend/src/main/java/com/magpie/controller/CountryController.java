package com.magpie.controller;

import com.magpie.entity.Country;
import com.magpie.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountryController {
    @Autowired
    private CountryRepository repo;
    @RequestMapping("/cust/countries")
    public List<Country> findAll() {
        return repo.findAll();
    }
}
