package it.unife.lp.view;

import java.io.File;

import it.unife.lp.MainApp;
import it.unife.lp.model.User;
import it.unife.lp.util.CFUtil;
import it.unife.lp.util.JsonController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserEditDialogController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField telField;
    @FXML
    private TextField cfField;

    private Stage dialogStage;
    private User user;
    private boolean okClicked = false;

    private MainApp mainApp;

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

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
        nameField.setText(user.getName());
        surnameField.setText(user.getSurname());
        telField.setText(user.getTel());
        cfField.setText(user.getCF());
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
            user.setName(nameField.getText());
            user.setSurname(surnameField.getText());
            user.setTel(telField.getText());
            user.setCF(cfField.getText());
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
        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Nome non valido!\n";
        }
        if (surnameField.getText() == null || surnameField.getText().length() == 0) {
            errorMessage += "Cognome non valido!\n";
        }
        if (telField.getText() == null || telField.getText().length() == 0 || telField.getText().length() > 10) {
            errorMessage += "Numero di telefono non valido!\n";
        }
        if (CFUtil.isCodiceFiscaleValido(cfField.getText())) {
            errorMessage += "Codice Fiscale non valido!\n";
        }

        for (User u : mainApp.getUsersData()) {
            if (u.getCF().equals(cfField.getText())) {
                errorMessage += "Utente già presente!\nCodice Fiscale già esistente!\n";
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
