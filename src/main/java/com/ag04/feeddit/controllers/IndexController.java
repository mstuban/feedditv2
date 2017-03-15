package com.ag04.feeddit.controllers;

import com.ag04.feeddit.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Autowired
    ProductRepository repository;

    @RequestMapping("/")
    String index() {
        return "index";
    }
}