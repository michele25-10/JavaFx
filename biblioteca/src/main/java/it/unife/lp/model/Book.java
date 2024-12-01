package it.unife.lp.model;

import java.time.LocalDate;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private int pubblicationYear;
    private String type;

    public Book(String isbn, String title, String author, int pubblicationYear, String type)
            throws IllegalArgumentException {
        if (!isValidISBN(isbn)) {
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

    // Validazione ISBN-13
    private static boolean isValidISBN(String isbn) {
        if (!isbn.matches("\\d{13}"))
            return false;

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = isbn.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }

        int checksum = 10 - (sum % 10);
        checksum = (checksum == 10) ? 0 : checksum;

        return checksum == (isbn.charAt(12) - '0');
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
