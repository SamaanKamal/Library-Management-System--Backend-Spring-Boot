package com.example.LibrarySystem.Controllers.test;

import com.example.LibrarySystem.Controller.BorrowingRecordController.BorrowingRecordController;
import com.example.LibrarySystem.Entity.Book;
import com.example.LibrarySystem.Entity.BorrowingRecord;
import com.example.LibrarySystem.Entity.Patron;
import com.example.LibrarySystem.Reposirtoy.BookRepository;
import com.example.LibrarySystem.Reposirtoy.PatronRepository;
import com.example.LibrarySystem.Service.BorrowingRecordService.BorrowingRecordService;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BorrowingRecordControllerTest {
    @Autowired
    private BorrowingRecordController borrowingRecordController;

    @MockBean
    private BorrowingRecordService borrowingRecordService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private PatronRepository patronRepository;

    @BeforeEach
    public void setup() {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("User"));
        Authentication authentication = new UsernamePasswordAuthenticationToken("username", "password", authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // Test borrowBook - Successful Borrowing

    @Test
    public void testBorrowBook_shouldReturnCreated_whenBorrowingSuccessful() throws Exception {
        Integer bookId = 1;
        Integer patronId = 1;
        BorrowingRecord borrowedRecord = new BorrowingRecord();
        borrowedRecord.setBook(new Book());
        borrowedRecord.setPatron(new Patron());
        borrowedRecord.setBorrowDate(LocalDate.now());

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(new Book()));
        Mockito.when(patronRepository.findById(patronId)).thenReturn(Optional.of(new Patron()));
        Mockito.when(borrowingRecordService.borrowBook(bookId, patronId)).thenReturn(borrowedRecord);

        ResponseEntity<?> response = borrowingRecordController.borrowBook(bookId, patronId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(borrowedRecord, response.getBody());
    }

    // Test borrowBook - Book Not Found

    @Test
    public void testBorrowBook_shouldReturnBadRequest_whenBookNotFound() throws Exception {
        Integer bookId = 1;
        Integer patronId = 1;

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = borrowingRecordController.borrowBook(bookId, patronId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(((String) response.getBody()).contains("Failed to borrow book"));
    }

    // Test borrowBook - Patron Not Found

    @Test
    public void testBorrowBook_shouldReturnBadRequest_whenPatronNotFound() throws Exception {
        Integer bookId = 1;
        Integer patronId = 1;

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(new Book()));
        Mockito.when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = borrowingRecordController.borrowBook(bookId, patronId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(((String) response.getBody()).contains("Failed to borrow book"));
    }

    // Test borrowBook - Book Already Borrowed

//    @Test
//    public void testBorrowBook_shouldReturnBadRequest_whenBookAlreadyBorrowed() throws Exception {
//        Integer bookId = 1;
//        Integer patronId = 1;
//        Book book = new Book();
//        book.setAvailable(false);
//
//        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
//        Mockito.when(patronRepository.findById(patronId)).thenReturn(Optional.of(new Patron()));
//        Mockito.when(borrowingRecordService.borrowBook(bookId, patronId)).thenThrow(new RuntimeException("Book is already borrowed by this patron!"));
//
//        ResponseEntity<?> response = borrowingRecordController.borrowBook(bookId, patronId);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertTrue(((String) response.getBody()).contains("already borrowed"));
//    }

    // Test borrowBook - Bad Request for Missing Path Variables

    @Test
    public void testBorrowBook_shouldReturnBadRequest_whenMissingPathVariable() throws Exception {
        Integer bookId = 1;

        ResponseEntity<?> response = borrowingRecordController.borrowBook(bookId, null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        response = borrowingRecordController.borrowBook(null, 2);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // Test returnBook - Successful Return

    @Test
    public void testReturnBook_shouldReturnOk_whenReturnSuccessful() throws Exception {
        Integer bookId = 1;
        Integer patronId = 1;
        Book book = new Book();
        book.setAvailable(false);
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(new Patron());
        borrowingRecord.setBorrowDate(LocalDate.now());
        borrowingRecord.setReturnDate(LocalDate.now());

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(patronRepository.findById(patronId)).thenReturn(Optional.of(new Patron()));
        Mockito.when(borrowingRecordService.returnBook(bookId, patronId)).thenReturn(borrowingRecord);

        ResponseEntity<?> response = borrowingRecordController.returnBook(bookId, patronId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(borrowingRecord, response.getBody());
    }

    // Test returnBook - Book Not Found

    @Test
    public void testReturnBook_shouldReturnBadRequest_whenBookNotFound() throws Exception {
        Integer bookId = 1;
        Integer patronId = 1;

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = borrowingRecordController.returnBook(bookId, patronId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(((String) response.getBody()).contains("Failed to return book"));
    }

    // Test returnBook - Patron Not Found

    @Test
    public void testReturnBook_shouldReturnBadRequest_whenPatronNotFound() throws Exception {
        Integer bookId = 1;
        Integer patronId = 1;

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(new Book()));
        Mockito.when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = borrowingRecordController.returnBook(bookId, patronId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(((String) response.getBody()).contains("Failed to return book"));
    }

    // Test returnBook - Book Not Borrowed by Patron

//    @Test
//    public void testReturnBook_shouldReturnBadRequest_whenBookNotBorrowedByPatron() throws Exception {
//        Integer bookId = 1;
//        Integer patronId = 1;
//        Book book = new Book();
//        book.setAvailable(true);
//
//        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
//        Mockito.when(patronRepository.findById(patronId)).thenReturn(Optional.of(new Patron()));
//        Mockito.when(borrowingRecordService.returnBook(bookId, patronId)).thenThrow(new RuntimeException("Book is not currently borrowed by this patron!"));
//
//        ResponseEntity<?> response = borrowingRecordController.returnBook(bookId, patronId);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertTrue(((String) response.getBody()).contains("not currently borrowed"));
//    }

    // Test returnBook - Borrowing Record Not Found
//    @Test
//    public void testReturnBook_shouldReturnBadRequest_whenBorrowingRecordNotFound() throws Exception {
//        Integer bookId = 1;
//        Integer patronId = 1;
//
//        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(new Book()));
//        Mockito.when(patronRepository.findById(patronId)).thenReturn(Optional.of(new Patron()));
//        Mockito.when(borrowingRecordService.returnBook(bookId, patronId)).thenThrow(new RuntimeException("Borrowing Record not Found with Book Id: " + bookId + " and Patron Id: " + patronId));
//
//        ResponseEntity<?> response = borrowingRecordController.returnBook(bookId, patronId);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertTrue(((String) response.getBody()).contains("Borrowing Record not Found"));
//    }
}

