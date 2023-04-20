package com.example.expose;

import com.example.persistence.repositories.BookRepository;
import com.example.services.contract.IBookService;
import com.example.services.impl.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;

class BookControllerTest {

    //System under test: SUT
    BookController bookController; //clase servicio que queremos testear

    //Dependencias. que tiene la clase servicio. Esto de abajo es un objeto ficticio (un doble)
    @Mock
    IBookService iBookService;

    @Mock
    Model model;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        bookController = new BookController(iBookService);
    }


}