package com.philipuriarte.BookVault.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import services.ProductsRepository;

@Controller
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private ProductsRepository repo;
}
