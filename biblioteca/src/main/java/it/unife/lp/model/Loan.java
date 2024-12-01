package it.unife.lp.model;

import java.time.LocalDate;

import it.unife.lp.util.IsbnUtil;

public class Loan {
    private String isbn;
    private String name;
    private String surname;
    private LocalDate startLoan;
    private LocalDate endLoan;

    public Loan(String isbn, String name, String surname, LocalDate startLoan, LocalDate endLoan)
            throws IllegalArgumentException {
        if (!IsbnUtil.isValidISBN(isbn)) {
            throw new IllegalArgumentException("Codice ISBN non è valido" + isbn);
        }
        this.isbn = isbn;
        this.name = name;
        this.surname = surname;
        this.startLoan = startLoan;
        this.endLoan = endLoan;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getStartLoan() {
        return startLoan;
    }

    public LocalDate getEndLoan() {
        return endLoan;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setStartLoan(LocalDate startLoan) {
        this.startLoan = startLoan;
    }

    public void setEndLoan(LocalDate endLoan) {
        this.endLoan = endLoan;
    }
}
