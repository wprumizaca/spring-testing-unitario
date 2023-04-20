package com.example.services.impl;

import com.example.persistence.entities.Book;
import com.example.persistence.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    //System under test: SUT
    BookService bookService; //clase servicio que queremos testear

    //Dependencias. que tiene la clase servicio. Esto de abajo es un objeto ficticio (un doble)
    @Mock
    BookRepository bookRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository);
    }

    @Test
    void getAll() {
        //Given
        //when
        //then
        /**
         * Configurar respuestas/ Configurar el mock
         */
        when(bookRepository.findAll()).thenReturn(List.of());

        /**
         * Ejecutar el comportamiento a testear
         */

        List<Book> books = bookService.getAll();

        /**
         * Comprobaciones de JUnit
         */
        assertNotNull(books); //comprobar que esa lista de libros no es nula
        assertEquals(0,books.size()); //comprobar que tiene tamaño 0

        /**
         * Verificaciones Mockito
         */
        verify(bookRepository, times(1)).findAll(); //Que se llama SÓLO 1 vez al método find All
        //verify(bookRepository,atLeast(1) .findAll();
    }

    //Por cada tipo de respuesta deberíamos hacer un test indivudual
    @Test
    void findByIdFound() {
        when(bookRepository.findById(any())).thenReturn(Optional.of(new Book(1L, "book1","description")));

        /**
         * Ejecutar el comportamiento a testear
         */

        Optional<Book> book = Optional.ofNullable(bookService.findById(1L));

        /**
         * Comprobaciones de JUnit
         */
        assertNotNull(book); //comprobar que esa lista de libros no es nula
        assertTrue(book.isPresent());
        assertEquals("BOOK1",book.get().getTitle()); //comprobar que tiene tamaño 0

        /**
         * Verificaciones Mockito
         */
        verify(bookRepository, times(1)).findById(1L); //Que se llama SÓLO 1 vez al método find All
        //verify(bookRepository,atLeast(1) .findAll();
    }


}