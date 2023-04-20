package com.example.services.impl;

import com.example.persistence.entities.Book;
import com.example.persistence.repositories.BookRepository;
import com.example.services.contract.IBookService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {

    private final BookRepository bookRepository;

    /**
     * All books
     *
     */
    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    /**
     *A book by its Id
     */
    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(book.getTitle().toUpperCase());
                    return book;
                })
                .orElse(null);
    }
}
