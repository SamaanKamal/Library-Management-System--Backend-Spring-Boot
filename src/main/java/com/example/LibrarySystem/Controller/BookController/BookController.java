package com.example.LibrarySystem.Controller.BookController;

import com.example.LibrarySystem.Entity.Book;
import com.example.LibrarySystem.Helpers.BookHelper.BookRequest;
import com.example.LibrarySystem.Helpers.BookHelper.BookResponse;
import com.example.LibrarySystem.Service.BookService.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private IBookService bookService;

    @GetMapping
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<BookResponse> fetchBooks(){
        List<Book> books =bookService.getAllBooks();
        if(books==null){
            return ResponseEntity.notFound().build();
        }
        BookResponse bookResponse = new BookResponse(books);
        return ResponseEntity.ok(bookResponse);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Book> fetchBook(@PathVariable("id") Integer id){
        if(id==null){
            return ResponseEntity.badRequest().build();
        }
        Book book = bookService.getBook(id);
        if(book ==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Book> createBook(@RequestBody BookRequest bookRequest){
        if(bookRequest==null){
            return ResponseEntity.badRequest().build();
        }
        Book book = bookService.createBook(bookRequest);
        if(book!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(book);
        }
        return  ResponseEntity.internalServerError().build();
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Integer id,@RequestBody BookRequest bookRequest){
        if(bookRequest==null||id ==null){
            return ResponseEntity.badRequest().build();
        }
        Book book = bookService.updateBook(id,bookRequest);
        if(book!=null){
            return ResponseEntity.status(HttpStatus.OK).body(book);
        }
        return  ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Integer id){
        if(id ==null){
            return ResponseEntity.badRequest().build();
        }
        boolean isDeleted= bookService.deleteBook(id);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body("Book Deleted Successfully");
        }
        return ResponseEntity.internalServerError().build();
    }



}
