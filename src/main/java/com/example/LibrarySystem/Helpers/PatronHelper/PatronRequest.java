package com.example.LibrarySystem.Helpers.PatronHelper;

import jakarta.persistence.Column;

public class PatronRequest {
    private String name;
    private String contactInformation;

    public PatronRequest() {
    }

    public String getName() {
        return name;
    }

    public String getContactInformation() {
        return contactInformation;
    }
}
