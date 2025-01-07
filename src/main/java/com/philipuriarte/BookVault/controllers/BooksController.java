package com.philipuriarte.BookVault.controllers;

import com.philipuriarte.BookVault.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import services.ProductsRepository;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private ProductsRepository repo;

    @GetMapping({"", "/"})
    public String showProductList (Model model) {
        List<Book> products = repo.findAll();
        model.addAttribute("products", products);
        return "books/index";
    }
}
