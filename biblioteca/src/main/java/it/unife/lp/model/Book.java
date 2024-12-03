package it.unife.lp.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.unife.lp.util.IsbnUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {
    private final StringProperty isbn;
    private final StringProperty title;
    private final StringProperty author;
    private final IntegerProperty pubblicationYear;
    private final StringProperty type;
    private final BooleanProperty available;

    public Book() {
        this("", "", "", 0, "");
    }

    public Book(String isbn, String title, String author, int pubblicationYear, String type)
            throws IllegalArgumentException {
        // Validazioni
        if (!isbn.isEmpty() && !IsbnUtil.isValidISBN(isbn)) {
            throw new IllegalArgumentException("Codice ISBN non valido: " + isbn);
        }
        if (pubblicationYear > LocalDate.now().getYear()) {
            throw new IllegalArgumentException("Anno di pubblicazione non valido");
        }

        // Inizializzazione delle proprietà
        this.isbn = new SimpleStringProperty(isbn);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.pubblicationYear = new SimpleIntegerProperty(pubblicationYear);
        this.type = new SimpleStringProperty(type);
        this.available = new SimpleBooleanProperty(true); // Valore predefinito: disponibile
    }

    // Getter
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

    public boolean isAvailable() {
        return available.get();
    }

    // Setter
    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public void setPubblicationYear(int pubblicationYear) {
        this.pubblicationYear.set(pubblicationYear);
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public void setAvailable(boolean available) {
        this.available.set(available);
    }

    // Property accessors
    public StringProperty isbnProperty() {
        return isbn;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty authorProperty() {
        return author;
    }

    public IntegerProperty pubblicationYearProperty() {
        return pubblicationYear;
    }

    public StringProperty typeProperty() {
        return type;
    }

    public BooleanProperty availableProperty() {
        return available;
    }

    public boolean getAvailable() {
        return available.get();
    }

    @JsonIgnore
    public String getPubblicationYearString() {
        return pubblicationYear.getValue().toString();
    }

}
