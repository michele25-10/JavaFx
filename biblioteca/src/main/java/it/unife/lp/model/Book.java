package it.unife.lp.model;

import java.time.LocalDate;

import it.unife.lp.util.IsbnUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Book {
    private StringProperty isbn;
    private StringProperty title;
    private StringProperty author;
    private IntegerProperty pubblicationYear;
    private StringProperty type;
    private boolean available;

    // Costruttore predefinito (obbligatorio per Jackson)
    public Book() {
    }

    public Book(StringProperty isbn, StringProperty title, StringProperty author, IntegerProperty pubblicationYear,
            StringProperty type)
            throws IllegalArgumentException {
        if (!IsbnUtil.isValidISBN(isbn.get())) {
            throw new IllegalArgumentException("Codice ISBN non è valido" + isbn);
        }
        if (pubblicationYear.get() > LocalDate.now().getYear()) {
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
        return isbn.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getAuthor() {
        return author.get();
    }

    public int getPubblicationYear() {
        return pubblicationYear.get();
    }

    public String getType() {
        return type.get();
    }

    public boolean getAvailable() {
        return available;
    }

    public void setIsbn(StringProperty isbn) {
        this.isbn = isbn;
    }

    public void setAuthor(StringProperty author) {
        this.author = author;
    }

    public void setTitle(StringProperty title) {
        this.title = title;
    }

    public void setPubblicationYear(IntegerProperty pubblicationYear) {
        this.pubblicationYear = pubblicationYear;
    }

    public void setType(StringProperty type) {
        this.type = type;
    }

    public StringProperty titleProperty() {
        return this.title;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
