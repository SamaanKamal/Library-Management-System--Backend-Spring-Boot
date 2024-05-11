package com.example.LibrarySystem.Reposirtoy;

import com.example.LibrarySystem.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Integer> {
}
