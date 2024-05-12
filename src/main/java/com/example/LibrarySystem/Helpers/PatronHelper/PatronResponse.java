package com.example.LibrarySystem.Helpers.PatronHelper;

import com.example.LibrarySystem.Entity.Patron;

import java.util.List;

public class PatronResponse {
    List<Patron> patrons;

    public PatronResponse(List<Patron> patrons) {
        this.patrons = patrons;
    }
}
