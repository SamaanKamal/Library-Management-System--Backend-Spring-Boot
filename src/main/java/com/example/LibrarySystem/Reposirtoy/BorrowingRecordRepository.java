package com.example.LibrarySystem.Reposirtoy;

import com.example.LibrarySystem.Entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord,Integer> {
}
