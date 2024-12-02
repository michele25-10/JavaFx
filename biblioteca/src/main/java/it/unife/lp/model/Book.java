package it.unife.lp.model;

import java.time.LocalDate;

import it.unife.lp.util.IsbnUtil;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private int pubblicationYear;
    private String type;

    // Costruttore predefinito (obbligatorio per Jackson)
    public Book() {
    }

    public Book(String isbn, String title, String author, int pubblicationYear, String type)
            throws IllegalArgumentException {
        if (!IsbnUtil.isValidISBN(isbn)) {
            throw new IllegalArgumentException("Codice ISBN non è valido" + isbn);
        }
        if (pubblicationYear > LocalDate.now().getYear()) {
            throw new IllegalArgumentException("L\'anno di pubblicazione non è valido");
        }

        // Aggiungere il controllo per capire se l'isbn è univoco o meno.

        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.pubblicationYear = pubblicationYear;
        this.type = type;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPubblicationYear() {
        return pubblicationYear;
    }

    public String getType() {
        return type;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPubblicationYear(int pubblicationYear) {
        this.pubblicationYear = pubblicationYear;
    }

    public void setType(String type) {
        this.type = type;
    }
}
