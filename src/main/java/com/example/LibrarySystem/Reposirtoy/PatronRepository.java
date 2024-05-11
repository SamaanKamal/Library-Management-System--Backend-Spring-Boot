package com.example.LibrarySystem.Reposirtoy;

import com.example.LibrarySystem.Entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron,Integer> {
}
