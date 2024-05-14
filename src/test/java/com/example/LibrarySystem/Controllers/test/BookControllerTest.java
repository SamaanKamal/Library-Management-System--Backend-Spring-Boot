package com.example.LibrarySystem.Controllers.test;



import com.example.LibrarySystem.Controller.BookController.BookController;
import com.example.LibrarySystem.Entity.Book;
import com.example.LibrarySystem.Helpers.BookHelper.BookRequest;
import com.example.LibrarySystem.Helpers.BookHelper.BookResponse;
import com.example.LibrarySystem.Service.BookService.IBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookControllerTest {

    @Autowired
    private BookController bookController;
    @MockBean
    private IBookService bookService;
    @MockBean
    private SecurityContext securityContext;

    @BeforeEach
    public void setupSecurityContext() {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("User"));
        Authentication authentication = new UsernamePasswordAuthenticationToken("username", "password", authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    @Test
    public void testGetAllBooks_shouldReturnListOfBooks() throws Exception {
        Book book = new Book("Test Book", "John Doe", 2023,"1234567890","second","test Book second edition",true);
        book.setBookId(10);
        List<Book> books = Arrays.asList(book);

        Mockito.when(bookService.getAllBooks()).thenReturn(books);

        ResponseEntity<BookResponse> response = bookController.fetchBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getBooks().size());
        assertEquals("Test Book", response.getBody().getBooks().get(0).getTitle());
    }


    @Test
    public void testFetchBook_shouldReturnBook_whenFound() throws Exception {
        Integer id = 1;
        Book existingBook = new Book("Test Book", "John Doe", 2023,"1234567890","second","test Book second edition",true);
        existingBook.setBookId(id);

        Mockito.when(bookService.getBook(id)).thenReturn(existingBook);

        ResponseEntity<Book> response = bookController.fetchBook(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getBookId());
        assertEquals("Test Book", response.getBody().getTitle());
    }

    @Test
    public void testFetchBook_shouldReturnNotFound_whenBookNotFound() throws Exception {
        Integer id = 1;

        Mockito.when(bookService.getBook(id)).thenReturn(null);

        ResponseEntity<Book> response = bookController.fetchBook(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testFetchBook_shouldReturnBadRequest_whenIdIsNull() throws Exception {
        ResponseEntity<Book> response = bookController.fetchBook(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    public void testCreateBook_shouldCreateBookAndReturnCreatedStatus() throws Exception {
        BookRequest bookRequest = new BookRequest("New Book", "Jane Doe", 2024, "123-456-7890", "1st", "A great book!");

        Book createdBook = new Book("New Book", "Jane Doe", 2024,"1234567890","second","test Book second edition",true);
        createdBook.setBookId(10);
        Mockito.when(bookService.createBook(bookRequest)).thenReturn(createdBook);

        ResponseEntity<Book> response = bookController.createBook(bookRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("New Book", response.getBody().getTitle());
        assertEquals("Jane Doe", response.getBody().getAuthor());
    }

    @Test
    public void testCreateBook_shouldReturnBadRequest_whenBookRequestIsNull() throws Exception {
        ResponseEntity<Book> response = bookController.createBook(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdateBook_shouldUpdateBookAndReturnOkStatus() throws Exception {
        Integer id = 1;
        Book existingBook = new Book("Old Title", "John Doe", 2023,"1234567890","second","test Book second edition",true);
        existingBook.setBookId(id);
        BookRequest updateRequest = new BookRequest("Updated Title", "John Doe", 2024, "10101010100101", "third", "third for the author");

        Book updatedBook = new Book("Updated Title", "John Doe", 2024,"1234567890","second","test Book second edition",true);
        updatedBook.setBookId(id);
        Mockito.when(bookService.getBook(id)).thenReturn(existingBook);
        Mockito.when(bookService.updateBook(id, updateRequest)).thenReturn(updatedBook);

        ResponseEntity<Book> response = bookController.updateBook(id, updateRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Title", response.getBody().getTitle());
        assertEquals(2024, response.getBody().getPublicationYear());
    }

    @Test
    public void testUpdateBook_shouldReturnBadRequest_whenBookRequestIsNull() throws Exception {
        Integer id = 1;

        ResponseEntity<Book> response = bookController.updateBook(id, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdateBook_shouldReturnInternalServerError_whenBookNotFound() throws Exception {
        Integer id = 1;
        BookRequest updateRequest = new BookRequest("Updated Title", "John Doe", 2024, null, null, null);

        Mockito.when(bookService.getBook(id)).thenReturn(null);

        ResponseEntity<Book> response = bookController.updateBook(id, updateRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    public void testDeleteBook_shouldReturnOk_whenBookDeleted() throws Exception {
        Integer id = 1;

        Mockito.when(bookService.deleteBook(id)).thenReturn(true);

        ResponseEntity<String> response = bookController.deleteBook(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book Deleted Successfully", response.getBody());
    }

    @Test
    public void testDeleteBook_shouldReturnInternalServerError_whenBookNotFound() throws Exception {
        Integer id = 1;

        Mockito.when(bookService.deleteBook(id)).thenReturn(false);

        ResponseEntity<String> response = bookController.deleteBook(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteBook_shouldReturnBadRequest_whenIdIsNull() throws Exception {
        ResponseEntity<String> response = bookController.deleteBook(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
