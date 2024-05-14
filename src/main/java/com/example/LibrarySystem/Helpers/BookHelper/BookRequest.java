package com.example.LibrarySystem.Helpers.BookHelper;


public class BookRequest {
    private String title;
    private String author;
    private int publicationYear;
    private String isbn;
    private String edition;
    private String description;

    public BookRequest(String title, String author, int publicationYear, String isbn, String edition, String description) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.edition = edition;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getEdition() {
        return edition;
    }

    public String getDescription() {
        return description;
    }
}
