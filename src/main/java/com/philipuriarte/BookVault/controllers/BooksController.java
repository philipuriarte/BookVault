package com.philipuriarte.BookVault.controllers;

import com.philipuriarte.BookVault.models.Book;
import com.philipuriarte.BookVault.models.BookDto;
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

    @GetMapping({"", "/home"})
    public String showBookList(Model model) {
        List<Book> books = repo.findAll();
        model.addAttribute("books", books);
        return "books/home";
    }

    @GetMapping()
    public String showCreatePage(Model model) {
        BookDto bookDto = new BookDto();
        model.addAttribute("bookDto", bookDto);
        return "books/addBook";
    }
}
