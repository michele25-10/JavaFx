package it.unife.lp.view;

import java.time.LocalDate;
import java.util.stream.Collectors;

import it.unife.lp.MainApp;
import it.unife.lp.model.Book;
import it.unife.lp.model.Loan;
import it.unife.lp.model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BookAvailableOverviewController {
    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, String> isbnColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, Integer> pubblicationYearColumn;
    @FXML
    private TableColumn<Book, String> typeColumn;
    @FXML
    private TextField filterField;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public BookAvailableOverviewController() {
    }

    @FXML
    private void initialize() {
        titleColumn.setCellValueFactory(
                cellData -> cellData.getValue().titleProperty());
        isbnColumn.setCellValueFactory(
                cellData -> cellData.getValue().isbnProperty());
        authorColumn.setCellValueFactory(
                cellData -> cellData.getValue().authorProperty());
        pubblicationYearColumn.setCellValueFactory(
                cellData -> cellData.getValue().pubblicationYearProperty().asObject());
        typeColumn.setCellValueFactory(
                cellData -> cellData.getValue().typeProperty());

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            // La funzione che vuoi eseguire quando cambia il testo
            onChangeFilterField(newValue.toLowerCase());
        });

    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        showBookList();
    }

    private void showBookList() {
        bookTable.setItems(mainApp.getBooksData().stream()
                .filter(book -> book.isAvailable())
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }

    /**
     * Metodo di utilità per controllare se una stringa contiene un'altra stringa in
     * modo case-insensitive.
     *
     * @param cellValue  Il valore della cella.
     * @param searchText Il testo da cercare.
     * @return True se il valore della cella contiene il testo di ricerca,
     *         altrimenti False.
     */
    private boolean containsIgnoreCase(String cellValue, String searchText) {
        return cellValue != null && cellValue.toLowerCase().contains(searchText);
    }

    @FXML
    private void onChangeFilterField(String search) {
        if (!search.isEmpty()) {
            ObservableList<Book> filteredBooks = bookTable.getItems().stream()
                    .filter(book -> {
                        // Ottieni i valori delle celle e controlla se contengono il testo di
                        // ricerca
                        return containsIgnoreCase(titleColumn.getCellData(book), search) ||
                                containsIgnoreCase(isbnColumn.getCellData(book),
                                        search)
                                ||
                                containsIgnoreCase(authorColumn.getCellData(book), search)
                                ||
                                containsIgnoreCase(typeColumn.getCellData(book),
                                        search);
                    })
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));

            // Aggiorna la TableView con i dati filtrati
            bookTable.setItems(filteredBooks);
        } else {
            // Se la ricerca è vuota, ripristina la lista originale
            showBookList();
        }
    }
}
