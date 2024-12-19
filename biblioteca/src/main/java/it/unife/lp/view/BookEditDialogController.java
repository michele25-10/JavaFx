package it.unife.lp.view;

import java.io.File;

import it.unife.lp.MainApp;
import it.unife.lp.model.Book;
import it.unife.lp.model.User;
import it.unife.lp.util.IsbnUtil;
import it.unife.lp.util.JsonController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BookEditDialogController {
    @FXML
    private TextField isbnField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField pubblicationYearField;

    private MainApp mainApp;
    private Stage dialogStage;
    private Book book;
    private boolean okClicked = false;

    /**
     * Initializesthe controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param user
     */
    public void setBook(Book book) {
        this.book = book;
        isbnField.setText(book.getIsbn());
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        typeField.setText(book.getType());
        pubblicationYearField.setText(book.getPubblicationYearString());
    }

    /**
     * Returnstrue if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            book.setIsbn(isbnField.getText());
            book.setTitle(titleField.getText());
            book.setAuthor(authorField.getText());
            book.setType(typeField.getText());
            book.setPubblicationYear(Integer.parseInt(pubblicationYearField.getText()));
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
        if (IsbnUtil.isValidISBN(isbnField.getText())) {
            errorMessage += "ISBN non valido!\n";
        }
        if (titleField.getText() == null || titleField.getText().length() == 0) {
            errorMessage += "Titolo non valido!\n";
        }
        if (authorField.getText() == null || authorField.getText().length() == 0) {
            errorMessage += "Nome autore non valido!\n";
        }
        if (typeField.getText() == null || typeField.getText().length() == 0) {
            errorMessage += "Genere non valido!\n";
        }
        try {
            Integer.parseInt(pubblicationYearField.getText());
        } catch (Exception e) {
            errorMessage += "Anno di pubblicazione non valido!\n";
        }

        for (Book b : mainApp.getBooksData()) {
            if (b.getIsbn().equals(isbnField.getText())) {
                errorMessage += "Codice ISBN già presente, ricontrollalo!\n";
                break;
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Errore di validazione");
            alert.setHeaderText("Per piacere ricontrolla i campi dati");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
