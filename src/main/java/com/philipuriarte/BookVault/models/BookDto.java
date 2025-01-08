package com.philipuriarte.BookVault.models;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class BookDto {
    @NotEmpty(message = "The title is required.")
    private String title;

    @NotEmpty(message = "The author is required.")
    private String author;

    @NotEmpty(message = "The publisher is required.")
    private String publisher;

    @NotEmpty(message = "The genre is required.")
    private String genre;

    private MultipartFile imageFile;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
