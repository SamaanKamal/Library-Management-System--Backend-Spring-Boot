package com.example.LibrarySystem.Authentication.Repository;

import com.example.LibrarySystem.Authentication.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Integer> {
    Optional<Users> findByEmail(String email);

}
