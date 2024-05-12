package com.example.LibrarySystem.Reposirtoy;

import com.example.LibrarySystem.Entity.Book;
import com.example.LibrarySystem.Entity.BorrowingRecord;
import com.example.LibrarySystem.Entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord,Integer> {
    Optional<BorrowingRecord> findByBookAndPatron(Book book, Patron patron);

//    boolean existsByBookIdAndReturnDateIsNull(Integer bookId);
//
//    boolean existsByBookIdAndPatronIdAndReturnDateIsNull(Integer bookId, Integer patronId);
}
