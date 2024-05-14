package com.example.LibrarySystem.Service.BorrowingRecordService;

import com.example.LibrarySystem.Entity.Book;
import com.example.LibrarySystem.Entity.BorrowingRecord;
import com.example.LibrarySystem.Entity.Patron;
import com.example.LibrarySystem.Reposirtoy.BookRepository;
import com.example.LibrarySystem.Reposirtoy.BorrowingRecordRepository;
import com.example.LibrarySystem.Reposirtoy.PatronRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
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
        Optional<BorrowingRecord> existingRecord = borrowingRecordRepository.findByBookAndPatron(book,patron);
        if(existingRecord.isPresent()&&existingRecord.get().getReturnDate()==null&&!book.isAvailable()){
            throw new RuntimeException("Book is already borrowed by this patron!");
        }

//        if(!isBookAvailable(bookId)){
//            try {
//                throw new BadRequestException("Book is currently unavailable for borrowing!");
//            } catch (BadRequestException e) {
//                throw new RuntimeException(e);
//            }
//        }
        book.setAvailable(false);
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
    public BorrowingRecord returnBook(Integer bookId, Integer patronId) {
        Optional<Book> book = bookRepository.findById(bookId);
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookAndPatron(bookRepository.findById(bookId).get(), patronRepository.findById(patronId).get())
                .orElseThrow(() -> new RuntimeException("Borrowing Record not Found with Book Id: " + bookId  + " and Patron Id: "+ patronId));

        if(borrowingRecord==null&&borrowingRecord.getReturnDate()!=null&&book.get().isAvailable()){
            throw new RuntimeException("Book is not currently borrowed by this patron!");
        }
        borrowingRecord.setReturnDate(LocalDate.now());

        BorrowingRecord savedBorrowingRecord =borrowingRecordRepository.save(borrowingRecord);
        if(savedBorrowingRecord!=null){
            return savedBorrowingRecord;
        }
        return null;
    }

//    public boolean isBookAvailable(Integer bookId) {
//        return !borrowingRecordRepository.existsByBookIdAndReturnDateIsNull(bookId);
//    }
//    public boolean isBookBorrowedByPatron(Integer bookId, Integer patronId) {
//        return borrowingRecordRepository.existsByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId);
//    }
}
