package com.example.LibrarySystem.Controller.BorrowingRecordController;

import com.example.LibrarySystem.Entity.Book;
import com.example.LibrarySystem.Entity.BorrowingRecord;
import com.example.LibrarySystem.Reposirtoy.BookRepository;
import com.example.LibrarySystem.Reposirtoy.PatronRepository;
import com.example.LibrarySystem.Service.BorrowingRecordService.BorrowingRecordService;
import com.example.LibrarySystem.Service.BorrowingRecordService.IBorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BorrowingRecordController {

    @Autowired
    private BorrowingRecordService borrowingRecordService;


    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<?> borrowBook(@PathVariable("bookId") Integer bookId, @PathVariable("patronId") Integer patronId) {
//        if (!borrowingRecordService.isBookAvailable(bookId)) {
//            return new ResponseEntity<>("The book is already borrowed", HttpStatus.BAD_REQUEST);
//        }
        if(bookId==null){
            return ResponseEntity.badRequest().body("Bad Request, Book Id is not Specified");
        }
        if(patronId==null){
            return ResponseEntity.badRequest().body("Bad Request, Patron Id is not Specified");
        }

        BorrowingRecord borrowed = borrowingRecordService.borrowBook(bookId, patronId);
        if (borrowed!=null) {
            return new ResponseEntity<>(borrowed, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to borrow book", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<?> returnBook(@PathVariable Integer bookId, @PathVariable Integer patronId) {
//        boolean bookBorrowedByPatron = borrowingRecordService.isBookBorrowedByPatron(bookId, patronId);
//        if (!bookBorrowedByPatron) {
//            return new ResponseEntity<>("The book is not currently borrowed by the specified patron", HttpStatus.BAD_REQUEST);
//        }
        if(bookId==null){
            return ResponseEntity.badRequest().body("Bad Request, Book Id is not Specified");
        }
        if(patronId==null){
            return ResponseEntity.badRequest().body("Bad Request, Patron Id is not Specified");
        }


        BorrowingRecord returned = borrowingRecordService.returnBook(bookId, patronId);
        if (returned!=null) {
            return new ResponseEntity<>(returned, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to return book", HttpStatus.BAD_REQUEST);
        }
    }

}
