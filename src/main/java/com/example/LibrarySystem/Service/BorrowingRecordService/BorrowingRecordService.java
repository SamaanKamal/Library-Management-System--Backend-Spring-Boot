package com.example.LibrarySystem.Service.BorrowingRecordService;

import com.example.LibrarySystem.Entity.Book;
import com.example.LibrarySystem.Entity.BorrowingRecord;
import com.example.LibrarySystem.Entity.Patron;
import com.example.LibrarySystem.Reposirtoy.BookRepository;
import com.example.LibrarySystem.Reposirtoy.BorrowingRecordRepository;
import com.example.LibrarySystem.Reposirtoy.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class BorrowingRecordService implements IBorrowingRecordService{
    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PatronRepository patronRepository;
    @Override
    public BorrowingRecord borrowBook(Integer bookId, Integer patronId) {
        Book book = bookRepository.findById(bookId).orElseThrow(()->
                new RuntimeException("Book not found with id:"  + bookId));
        Patron patron= patronRepository.findById(patronId).orElseThrow(()->
                new RuntimeException("Patron not found with id:"  + patronId));

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());
        BorrowingRecord savedBorrowingRecord = borrowingRecordRepository.save(borrowingRecord);
        if(savedBorrowingRecord!=null){
            return borrowingRecord;
        }
        return null;
    }

    @Override
    public boolean returnBook(Integer bookId, Integer patronId) {
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookAndPatron(bookRepository.findById(bookId).get(), patronRepository.findById(patronId).get())
                .orElseThrow(() -> new RuntimeException("Borrowing record not found"));

        // Update the returned date in the borrowing record
        borrowingRecord.setReturnDate(LocalDate.now());

        // Save the updated borrowing record
        BorrowingRecord savedBorrowingRecord =borrowingRecordRepository.save(borrowingRecord);
        if(savedBorrowingRecord!=null){
            return true;
        }
        return false;
    }
    public boolean isBookAvailable(Integer bookId) {
        return !borrowingRecordRepository.existsByBookIdAndReturnedDateIsNull(bookId);
    }
}
