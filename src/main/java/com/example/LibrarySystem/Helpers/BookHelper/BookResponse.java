package com.example.LibrarySystem.Helpers.BookHelper;

import com.example.LibrarySystem.Entity.Book;

import java.util.List;

public class BookResponse {
    List<Book> books;

    public BookResponse(List<Book> books) {
        this.books = books;
    }
}
