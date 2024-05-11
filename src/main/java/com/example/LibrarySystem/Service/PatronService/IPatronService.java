package com.example.LibrarySystem.Service.PatronService;


import com.example.LibrarySystem.Entity.Patron;
import com.example.LibrarySystem.Helpers.PatronHelper.PatronRequest;

import java.util.List;

public interface IPatronService {
    List<Patron> getAllPatrons();
    Patron getPatron(Integer patronId);
    boolean createPatron(PatronRequest patronRequest);
    Patron updatePatron(Integer patronId,PatronRequest patronRequest);
    boolean deletePatron(Integer patronId);
}
