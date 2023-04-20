package com.example.services.contract;

import com.example.persistence.entities.Book;

import java.util.List;

public interface IBookService {
    List<Book> getAll();

    Book findById(Long id);

}
