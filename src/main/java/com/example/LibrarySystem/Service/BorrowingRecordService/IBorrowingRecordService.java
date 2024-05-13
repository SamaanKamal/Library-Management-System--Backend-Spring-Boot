package com.example.LibrarySystem.Service.BorrowingRecordService;

import com.example.LibrarySystem.Entity.BorrowingRecord;

public interface IBorrowingRecordService {
    BorrowingRecord borrowBook(Integer bookId, Integer patronId);
    BorrowingRecord returnBook(Integer bookId, Integer patronId);
}
