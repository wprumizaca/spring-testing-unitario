package com.example.expose;

import com.example.expose.controller.BookController;
import com.example.services.contract.IBookService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

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