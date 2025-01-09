package com.philipuriarte.BookVault.services;

import com.philipuriarte.BookVault.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Book, Integer>  {

}
