package com.example.LibrarySystem.Service.BookService;

import com.example.LibrarySystem.Entity.Book;
import com.example.LibrarySystem.Helpers.BookHelper.BookRequest;
import com.example.LibrarySystem.Reposirtoy.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBook(Integer bookId) {
        return bookRepository.findById(bookId).orElseThrow(()->
                new RuntimeException("Book not found with id:"  + bookId));
    }

    @Override
    public Book createBook(BookRequest bookRequest) {
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setPublicationYear(bookRequest.getPublicationYear());
        book.setIsbn(bookRequest.getIsbn());
        book.setEdition(bookRequest.getEdition());
        book.setDescription(bookRequest.getDescription());
        Book savedBook = bookRepository.save(book);
        if(savedBook!=null){
            return savedBook;
        }
        return null;
    }

    @Override
    public Book updateBook(Integer bookId, BookRequest bookRequest) {
        Book book =bookRepository.findById(bookId).orElseThrow(()->
                new RuntimeException("Book not found with id:"  + bookId));
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setPublicationYear(bookRequest.getPublicationYear());
        book.setIsbn(bookRequest.getIsbn());
        book.setEdition(bookRequest.getEdition());
        book.setDescription(bookRequest.getDescription());
        bookRepository.save(book);
        return book;
    }

    @Override
    public boolean deleteBook(Integer bookId) {
        Book book =bookRepository.findById(bookId).orElseThrow(()->
                new RuntimeException("Book not found with id:"  + bookId));
        if(book!=null){
            bookRepository.delete(book);
            return true;
        }
        return false;
    }
}
