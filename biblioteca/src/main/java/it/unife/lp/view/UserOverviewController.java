package it.unife.lp.view;

import java.io.File;
import java.time.LocalDate;
import java.util.stream.Collectors;

import it.unife.lp.MainApp;
import it.unife.lp.model.Book;
import it.unife.lp.model.Loan;
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
    @FXML
    private Label cfLabel;
    @FXML
    private TextField filterField;

    @FXML
    private TableView<Loan> loanTable;
    @FXML
    private TableColumn<Loan, String> isbnColumn;
    @FXML
    private TableColumn<Loan, String> titleColumn;
    @FXML
    private TableColumn<Loan, LocalDate> endDateColumn;
    @FXML
    private TableColumn<Loan, LocalDate> startDateColumn;

    // Reference to the main application.
    private MainApp mainApp;

    private Loan selectedLoan;

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

        // Initialize the person table with the two columns.
        isbnColumn.setCellValueFactory(
                cellData -> cellData.getValue().isbnProperty());
        titleColumn.setCellValueFactory(
                cellData -> cellData.getValue().titleProperty());
        endDateColumn.setCellValueFactory(
                cellData -> cellData.getValue().endLoanProperty());
        startDateColumn.setCellValueFactory(
                cellData -> cellData.getValue().startLoanProperty());

        // Listen for selection changes and show the person details when changed.
        loanTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectedLoan = newValue);
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
            cfLabel.setText(user.getCF());
            telLabel.setText(user.getTel());

            // resetto l'ultimo loan selezionato
            selectedLoan = null;

            showUserLoanDetails(user);
        } else {
            // Person is null, remove all the text.
            nameLabel.setText("");
            surnameLabel.setText("");
            telLabel.setText("");
            cfLabel.setText("");
        }
    }

    private void showUserLoanDetails(User user) {
        if (user != null) {
            loanTable.setItems(mainApp.getLoansData().stream().filter(
                    loan -> loan.getCF().equals(user.getCF())
                            && !loan.isFinished())
                    .collect(Collectors.toCollection(FXCollections::observableArrayList)));
        }
    }

    @FXML
    private void handleDeleteUser() {
        int selectedIndex = userTable.getSelectionModel().getSelectedIndex();
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedIndex >= 0) {
            // rimuovo quell'utente dalla lista degli utenti
            userTable.getItems().remove(selectedIndex);

            // rimuovo i prestiti di quell'utente dalla lista dei prestiti

            // I libri che erano stati prestati a quell'utente tornano ad essere disponibili

            JsonController.writeAll(new File(MainApp.dataDir + File.separator + "user.json"), mainApp.getUsersData());
            JsonController.writeAll(new File(MainApp.dataDir + File.separator + "loan.json"), mainApp.getLoansData());
            JsonController.writeAll(new File(MainApp.dataDir + File.separator + "book.json"), mainApp.getBooksData());
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

    @FXML
    private void onChangeFilterField() {
        String search = filterField.getText().toLowerCase();
        System.out.println("ricerca: " + search);

        if (!search.isEmpty()) {
            ObservableList<User> filteredUsers = mainApp.getUsersData().stream()
                    .filter(user -> user.getName().toLowerCase().contains(search)
                            || user.getSurname().toLowerCase().contains(search)
                            || user.getTel().toLowerCase().contains(search))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));

            System.out.println("Dati filtrati: " + filteredUsers);
            userTable.setItems(filteredUsers);
        } else {
            // Se la ricerca è vuota, ripristina la lista originale
            userTable.setItems(mainApp.getUsersData());
        }
    }

    // GESTIONE DEI LOAN
    @FXML
    private void handleNewLoan() {
        // prima di poter creare un prestito devi selezionare l'utente di interesse
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {

            Loan tempLoan = new Loan();
            boolean okClicked = mainApp.showUserLoanEditDialog(tempLoan, selectedUser);
            if (okClicked) {
                mainApp.getLoansData().add(tempLoan);

                // Rendo il libro non disponibile nel magazzino
                mainApp.getBooksData().forEach(book -> {
                    if (book.getIsbn().equals(tempLoan.getIsbn()) && book.getTitle().equals(tempLoan.getTitle())) {
                        book.setAvailable(false);
                    }
                });

                showUserLoanDetails(selectedUser);

                JsonController.writeAll(new File(MainApp.dataDir + File.separator + "loan.json"),
                        mainApp.getLoansData());
                JsonController.writeAll(new File(MainApp.dataDir + File.separator + "book.json"),
                        mainApp.getBooksData());
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

    @FXML
    private void handleEditLoan() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser != null && selectedLoan != null) {
            String previousSelectedIsbn = selectedLoan.getIsbn();
            String previousTitle = selectedLoan.getTitle();

            System.out.println(selectedLoan.getTitle() + " " + selectedLoan.getIsbn());

            boolean okClicked = mainApp.showUserLoanEditDialog(selectedLoan, selectedUser);
            if (okClicked) {
                // se ho modificato l'isbn allora devo andare a rendere disponibile il libro di
                // prima ed rendere non dispobibile il nuovo libro

                System.out.println(selectedLoan.getTitle() + " " + selectedLoan.getIsbn());
                if (!selectedLoan.getIsbn().equals(previousSelectedIsbn)
                        && !selectedLoan.getTitle().equals(previousTitle)) {
                    System.out.println("Sono qui dentro");

                    mainApp.getBooksData().forEach(book -> {
                        // Rendo il libro selezionato precedentemente disponibile
                        if (book.getIsbn().equals(previousSelectedIsbn) && book.getTitle().equals(previousTitle)) {
                            book.setAvailable(true);
                        }

                        // Rendo il libro modificato non disponibile a magazzino
                        if (book.getIsbn().equals(selectedLoan.getIsbn())
                                && book.getTitle().equals(selectedLoan.getTitle())) {
                            book.setAvailable(false);

                        }
                    });
                }

                showUserLoanDetails(selectedUser);

                JsonController.writeAll(new File(MainApp.dataDir + File.separator +
                        "loan.json"),
                        mainApp.getLoansData());

                JsonController.writeAll(new File(MainApp.dataDir + File.separator +
                        "book.json"),
                        mainApp.getBooksData());

            }
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Nessuna selezione");
            alert.setHeaderText("Nessun utente e prestito selezionato");
            alert.setContentText("Per favore seleziona un utente o un prestito dalla tabella");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteLoan() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            int selectedIndex = loanTable.getSelectionModel().getSelectedIndex();
            selectedLoan = loanTable.getSelectionModel().getSelectedItem();
            if (selectedIndex >= 0) {
                mainApp.getBooksData().forEach(book -> {
                    if (selectedLoan.getIsbn().equals(book.getIsbn())
                            && selectedLoan.getTitle().equals(book.getTitle())) {
                        book.setAvailable(true);
                    }
                });
                mainApp.getLoansData().remove(selectedLoan);

                showUserLoanDetails(selectedUser);

                JsonController.writeAll(new File(MainApp.dataDir + File.separator + "loan.json"),
                        mainApp.getLoansData());

                JsonController.writeAll(new File(MainApp.dataDir + File.separator + "book.json"),
                        mainApp.getBooksData());
            } else {
                // Nothing selected.
                Alert alert = new Alert(AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("Nessuna selezione");
                alert.setHeaderText("Nessun prestito selezionato");
                alert.setContentText("Per favore seleziona un prestito dell'utente dalla tabella");
                alert.showAndWait();
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

    @FXML
    private void handleFinishLoad() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            selectedLoan = loanTable.getSelectionModel().getSelectedItem();
            if (selectedLoan != null) {
                mainApp.getLoansData().forEach(loan -> {
                    if (loan.getIsbn().equals(selectedLoan.getIsbn())
                            && loan.getTitle().equals(selectedLoan.getTitle())) {
                        loan.setFinished(true);
                    }
                });

                mainApp.getBooksData().forEach(book -> {
                    if (book.getIsbn().equals(selectedLoan.getIsbn())
                            && book.getTitle().equals(selectedLoan.getTitle())) {
                        book.setAvailable(true);
                    }
                });

                showUserLoanDetails(selectedUser);

                JsonController.writeAll(new File(MainApp.dataDir + File.separator + "loan.json"),
                        mainApp.getLoansData());

                JsonController.writeAll(new File(MainApp.dataDir + File.separator + "book.json"),
                        mainApp.getBooksData());
            } else {
                // Nothing selected.
                Alert alert = new Alert(AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("Nessuna selezione");
                alert.setHeaderText("Nessun prestito selezionato");
                alert.setContentText("Per favore seleziona un prestito dell'utente dalla tabella");
                alert.showAndWait();
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
