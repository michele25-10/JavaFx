package it.unife.lp.view;

import java.io.File;
import java.util.stream.Collectors;

import it.unife.lp.MainApp;
import it.unife.lp.model.Book;
import it.unife.lp.model.User;
import it.unife.lp.util.JsonController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BookOverviewController {
    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private Label titleLabel;
    @FXML
    private Label isbnLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label pubblicationYearLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label availableLabel;
    @FXML
    private TextField filterField;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public BookOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        titleColumn.setCellValueFactory(
                cellData -> cellData.getValue().titleProperty());
        // Clear person details.
        showBookDetails(null);
        // Listen for selection changes and show the person details when changed.
        bookTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showBookDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        // Add observable list data to the table
        bookTable.setItems(mainApp.getBooksData());
    }

    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param book the book or null
     */
    private void showBookDetails(Book book) {
        if (book != null) {
            // Fill the labels with info from the person object.
            isbnLabel.setText(book.getIsbn());
            titleLabel.setText(book.getTitle());
            authorLabel.setText(book.getAuthor());
            pubblicationYearLabel.setText(book.getPubblicationYearString());
            typeLabel.setText(book.getType());
            availableLabel.setText(book.getAvailable() ? "Disponibile" : "NON disponibile");
        } else {
            // Person is null, remove all the text.
            isbnLabel.setText("");
            titleLabel.setText("");
            authorLabel.setText("");
            pubblicationYearLabel.setText("");
            typeLabel.setText("");
            availableLabel.setText("");
        }
    }

    @FXML
    private void handleDeleteBook() {
        int selectedIndex = bookTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            bookTable.getItems().remove(selectedIndex);
            JsonController.writeAll(new File(MainApp.dataDir + File.separator + "book.json"), mainApp.getBooksData());
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Nessuna selezione");
            alert.setHeaderText("Nessun libro selezionato");
            alert.setContentText("Per favore seleziona un libro dalla tabella");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewBook() {
        Book tempBook = new Book();
        boolean okClicked = mainApp.showBookEditDialog(tempBook);
        if (okClicked) {
            mainApp.getBooksData().add(tempBook);
            JsonController.writeAll(new File(MainApp.dataDir + File.separator +
                    "book.json"), mainApp.getBooksData());
        }

    }

    @FXML
    private void handleEditBook() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            boolean okClicked = mainApp.showBookEditDialog(selectedBook);
            if (okClicked) {
                showBookDetails(selectedBook);
                JsonController.writeAll(new File(MainApp.dataDir + File.separator +
                        "book.json"),
                        mainApp.getBooksData());
            }
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Nessuna selezione");
            alert.setHeaderText("Nessun libro selezionato");
            alert.setContentText("Per favore seleziona un libro dalla tabella");
            alert.showAndWait();
        }

    }

    @FXML
    private void onChangeFilterField() {
        String search = filterField.getText().toLowerCase();
        if (!search.isEmpty()) {
            ObservableList<Book> filteredBooks = mainApp.getBooksData().stream()
                    .filter(book -> book.getIsbn().toLowerCase().contains(search)
                            || book.getTitle().toLowerCase().contains(search)
                            || book.getAuthor().toLowerCase().contains(search)
                            || book.getType().toLowerCase().contains(search))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));

            bookTable.setItems(filteredBooks);
        } else {
            // Se la ricerca è vuota, ripristina la lista originale
            bookTable.setItems(mainApp.getBooksData());
        }
    }
}
