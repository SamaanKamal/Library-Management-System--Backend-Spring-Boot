package com.example.LibrarySystem.Service.BookService;

import com.example.LibrarySystem.Entity.Book;
import com.example.LibrarySystem.Helpers.BookHelper.BookRequest;

import java.util.List;

public interface IBookService {

    List<Book> getAllBooks();
    Book getBook(Integer bookId);
    boolean createBook(BookRequest bookRequest);
    Book updateBook(Integer bookId,BookRequest bookRequest);
    boolean deleteBook(Integer bookId);

}
