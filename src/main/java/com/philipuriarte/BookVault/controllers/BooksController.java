package com.philipuriarte.BookVault.controllers;

import com.philipuriarte.BookVault.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.philipuriarte.BookVault.services.BooksRepository;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BooksRepository repo;

    @GetMapping({"", "/"})
    public String showBookList (Model model) {
        List<Book> books = repo.findAll();
        model.addAttribute("books", books);
        return "books/index";
    }
}
