package com.example.LibrarySystem.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer bookId;
    @Column(name = "title")
    @NotBlank(message = "Title is mandatory")
    private String title;
    @Column(name = "author")
    @NotBlank(message = "Author is mandatory")
    private String author;
    @Column(name = "publication_year")
    @NotBlank(message = "publication year is mandatory")
    private int publicationYear;
    @Column(name = "isbn")
    @NotBlank(message = "ISBN is mandatory")
    @Size(min = 10, max = 13, message = "ISBN must be 10 or 13 characters long")
    private String isbn;
    @Column(name = "edition")
    private String edition;
    @Column(name = "description")
    private String description;

    @Column(name = "is_available")
    @NotBlank
    private boolean isAvailable;

    public Book() {
    }

    public Book(String title, String author, int publicationYear, String isbn, String edition, String description,boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.edition = edition;
        this.description = description;
        this.isAvailable=isAvailable;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
