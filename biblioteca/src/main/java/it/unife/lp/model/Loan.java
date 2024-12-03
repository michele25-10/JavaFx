package it.unife.lp.model;

import java.time.LocalDate;

import it.unife.lp.util.IsbnUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Loan {
    private final StringProperty isbn;
    private final StringProperty name;
    private final StringProperty surname;
    private final StringProperty title;
    private final ObjectProperty<LocalDate> startLoan;
    private final ObjectProperty<LocalDate> endLoan;
    private final BooleanProperty finished; // Nuova proprietà Boolean

    // Costruttore predefinito (obbligatorio per Jackson)
    public Loan() {
        this.isbn = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.surname = new SimpleStringProperty();
        this.title = new SimpleStringProperty();
        this.startLoan = new SimpleObjectProperty<>();
        this.endLoan = new SimpleObjectProperty<>();
        this.finished = new SimpleBooleanProperty(false); // Inizializzazione a false
    }

    // Costruttore con tutti i parametri
    public Loan(String isbn, String name, String surname, String title, LocalDate startLoan, LocalDate endLoan,
            boolean finished)
            throws IllegalArgumentException {
        if (!IsbnUtil.isValidISBN(isbn)) {
            throw new IllegalArgumentException("Codice ISBN non è valido: " + isbn);
        }
        this.isbn = new SimpleStringProperty(isbn);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.title = new SimpleStringProperty(title);
        this.startLoan = new SimpleObjectProperty<>(startLoan);
        this.endLoan = new SimpleObjectProperty<>(endLoan);
        this.finished = new SimpleBooleanProperty(finished); // Assegnazione del valore del parametro finished
    }

    // Getter per gli attributi
    public String getIsbn() {
        return isbn.get();
    }

    public String getName() {
        return name.get();
    }

    public String getSurname() {
        return surname.get();
    }

    public String getTitle() {
        return title.get();
    }

    public LocalDate getStartLoan() {
        return startLoan.get();
    }

    public LocalDate getEndLoan() {
        return endLoan.get();
    }

    public boolean isFinished() {
        return finished.get(); // Getter per la proprietà finished
    }

    // Setter per gli attributi
    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setStartLoan(LocalDate startLoan) {
        this.startLoan.set(startLoan);
    }

    public void setEndLoan(LocalDate endLoan) {
        this.endLoan.set(endLoan);
    }

    public void setFinished(boolean finished) {
        this.finished.set(finished); // Setter per la proprietà finished
    }

    // Properties per il binding con la UI
    public StringProperty isbnProperty() {
        return isbn;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public ObjectProperty<LocalDate> startLoanProperty() {
        return startLoan;
    }

    public ObjectProperty<LocalDate> endLoanProperty() {
        return endLoan;
    }

    public BooleanProperty finishedProperty() {
        return finished; // Metodo per il binding della proprietà finished
    }
}
