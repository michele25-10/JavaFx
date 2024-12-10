package it.unife.lp.view;

import java.time.LocalDate;
import java.util.List;

import it.unife.lp.model.Loan;
import it.unife.lp.model.User;
import it.unife.lp.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserLoanEditDialogController {
    @FXML
    private TextField endLoanDate;
    @FXML
    private TextField startLoanDate;
    @FXML
    private ComboBox<String> isbnTitleComboBox;

    private Stage dialogStage;
    private Loan loan;
    private User user;
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
     * Sets the person to be edited in the dialog.
     *
     * @param user
     */
    public void setLoan(Loan loan, User user, List<String> availableBooks) {
        this.loan = loan;
        this.user = user;
        isbnTitleComboBox.getItems().addAll(availableBooks);

        if (loan != null) {
            startLoanDate.setText(DateUtil.format(loan.getStartLoan()));
            endLoanDate.setText(DateUtil.format(loan.getEndLoan()));
            isbnTitleComboBox.setValue(loan.getIsbn() + " -> " + loan.getTitle());
        } else {
            startLoanDate.setText("");
            endLoanDate.setText("");
            isbnTitleComboBox.setValue("");
        }
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
            String[] selctedIsbnTitleArr = isbnTitleComboBox.getValue().split(" -> ");

            try {
                loan.setIsbn(selctedIsbnTitleArr[0]);
                loan.setTitle(selctedIsbnTitleArr[1]);
                loan.setCF(user.getCF());
                loan.setEndLoan(DateUtil.parse(endLoanDate.getText()));
                loan.setStartLoan(DateUtil.parse(startLoanDate.getText()));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

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
        if (!DateUtil.validDate(startLoanDate.getText())) {
            errorMessage += "Data di inizio prestito non valida!\n";
        }
        if (!DateUtil.validDate(endLoanDate.getText())) {
            errorMessage += "Data di fine prestito non valida!\n";
        }
        if (isbnTitleComboBox.getValue().isEmpty()) {
            errorMessage += "ISBN e titolo non valido!\n";
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
