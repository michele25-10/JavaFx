package it.unife.lp.view;

import java.io.File;

import it.unife.lp.MainApp;
import it.unife.lp.model.User;
import it.unife.lp.util.DateUtil;
import it.unife.lp.util.JsonController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UserOverviewController {
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> surnameColumn;
    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label telLabel;
    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public UserOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        nameColumn.setCellValueFactory(
                cellData -> cellData.getValue().nameProperty());
        surnameColumn.setCellValueFactory(
                cellData -> cellData.getValue().surnameProperty());
        // Clear person details.
        showUserDetails(null);
        // Listen for selection changes and show the person details when changed.
        userTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showUserDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        // Add observable list data to the table
        userTable.setItems(mainApp.getUsersData());
    }

    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param user the person or null
     */
    private void showUserDetails(User user) {
        if (user != null) {
            // Fill the labels with info from the person object.
            nameLabel.setText(user.getName());
            surnameLabel.setText(user.getSurname());
            telLabel.setText(user.getTel());
        } else {
            // Person is null, remove all the text.
            nameLabel.setText("");
            surnameLabel.setText("");
            telLabel.setText("");
        }
    }

    @FXML
    private void handleDeleteUser() {
        int selectedIndex = userTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            userTable.getItems().remove(selectedIndex);
            JsonController.writeAll(new File(MainApp.dataDir + File.separator + "user.json"), mainApp.getUsersData());
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Nessuna selezione");
            alert.setHeaderText("Nessun utente selezionato");
            alert.setContentText("Per favore seleziona un utente dalla tabella");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewUser() {
        User tempUser = new User();
        boolean okClicked = mainApp.showUserEditDialog(tempUser);
        if (okClicked) {
            mainApp.getUsersData().add(tempUser);
            JsonController.writeAll(new File(MainApp.dataDir + File.separator + "user.json"), mainApp.getUsersData());
        }
    }

    @FXML
    private void handleEditUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            boolean okClicked = mainApp.showUserEditDialog(selectedUser);
            if (okClicked) {
                showUserDetails(selectedUser);
                JsonController.writeAll(new File(MainApp.dataDir + File.separator + "user.json"),
                        mainApp.getUsersData());
            }
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Nessuna selezione");
            alert.setHeaderText("Nessun utente selezionato");
            alert.setContentText("Per favore seleziona un utente dalla tabella");
            alert.showAndWait();
        }
    }
}
