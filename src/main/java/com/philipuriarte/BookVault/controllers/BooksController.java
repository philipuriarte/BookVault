package com.philipuriarte.BookVault.controllers;

import com.philipuriarte.BookVault.models.Book;
import com.philipuriarte.BookVault.models.BookDto;
import com.philipuriarte.BookVault.services.BooksRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.StandardCopyOption;
import java.util.Date;
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

        // Save image file
        MultipartFile image = bookDto.getImageFile();
        String storageFileName = image.getOriginalFilename();

        try {
            String uploadDir = "public/images/";
            java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = image.getInputStream()){
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        Book book = new Book();
        Date dateAdded = new Date();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPublisher(bookDto.getPublisher());
        book.setGenre(bookDto.getGenre());
        book.setDateAdded(dateAdded);
        book.setImageFileName(storageFileName);

        repo.save(book);

        return "redirect:/books/home";
    }

    @GetMapping("/editbook")
    public String showEditPage (
            Model model,
            @RequestParam int id
            ) {

        try {
            Book book = repo.findById(id).get();
            model.addAttribute("book", book);

            BookDto bookDto = new BookDto();
            bookDto.setTitle(book.getTitle());
            bookDto.setAuthor(book.getAuthor());
            bookDto.setPublisher(book.getPublisher());
            bookDto.setGenre(book.getGenre());

            model.addAttribute("bookDto", bookDto);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/books/home";
        }

        return "books/editbook";
    }

    @PostMapping("/editbook")
    public String updateBook(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute BookDto bookDto,
            BindingResult result
            ) {

        try {
            Book book = repo.findById(id).get();
            model.addAttribute("book", book);

            if (result.hasErrors()) {
                return "/books/editbook";
            }

            if (!bookDto.getImageFile().isEmpty()) {
                // Delete old image
                String uploadDir = "public/images/";
                Path oldImagePath = Paths.get(uploadDir + book.getImageFileName());

                try {
                    Files.delete(oldImagePath);
                } catch (Exception ex) {
                    System.out.println("Exception: " + ex.getMessage());
                }

                // Save new image
                MultipartFile image =   bookDto.getImageFile();
                String storageFileName = image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }

                book.setImageFileName(storageFileName);
            }

            book.setTitle(bookDto.getTitle());
            book.setAuthor(bookDto.getAuthor());
            book.setPublisher(bookDto.getPublisher());
            book.setGenre(bookDto.getGenre());

            repo.save(book);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/books/home";
    }
}
