package com.philipuriarte.BookVault.controllers;

import com.philipuriarte.BookVault.models.Book;
import com.philipuriarte.BookVault.models.BookDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/addbook")
    public String showAddPage(Model model) {
        BookDto bookDto = new BookDto();
        model.addAttribute("bookDto", bookDto);
        return "books/addbook";
    }

    @PostMapping("/addbook")
    public String addBook(
            @Valid @ModelAttribute BookDto bookDto,
            BindingResult result
            ) {
        if (bookDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("bookDto", "imageFile", "The image file is required."));
        }

        if (result.hasErrors()) {
            return "books/addbook";
        }

        return "redirect:/books/home";
    }
}
