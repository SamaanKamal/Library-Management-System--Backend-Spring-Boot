package com.example.LibrarySystem.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "patron")
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patron_id")
    private Integer patronId;
    @Column(name = "name")
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Column(name = "contact_information")
    @NotBlank(message = "contact information is mandatory")
    private String contactInformation;

    public Patron() {
    }

    public Patron(String name, String contactInformation) {
        this.name = name;
        this.contactInformation = contactInformation;
    }

    public Integer getPatronId() {
        return patronId;
    }

    public void setPatronId(Integer patronId) {
        this.patronId = patronId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }
}
