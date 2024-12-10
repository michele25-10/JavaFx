package it.unife.lp.view;

import java.io.File;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.stream.Collectors;

import it.unife.lp.MainApp;
import it.unife.lp.model.Book;
import it.unife.lp.model.Loan;
import it.unife.lp.model.User;
import it.unife.lp.util.JsonController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoanOverviewController {
        @FXML
        private TableView<Loan> loanTable;
        @FXML
        private TableColumn<Loan, String> nameColumn;
        @FXML
        private TableColumn<Loan, String> surnameColumn;
        @FXML
        private TableColumn<Loan, String> telColumn;
        @FXML
        private TableColumn<Loan, String> titleColumn;
        @FXML
        private TableColumn<Loan, String> isbnColumn;
        @FXML
        private TableColumn<Loan, LocalDate> startLoanColumn;
        @FXML
        private TableColumn<Loan, LocalDate> endLoanColumn;
        @FXML
        private TextField filterField;

        // Reference to the main application.
        private MainApp mainApp;

        /**
         * The constructor.
         * The constructor is called before the initialize() method.
         */
        public LoanOverviewController() {
        }

        /**
         * Initializes the controller class. This method is automatically called
         * after the fxml file has been loaded.
         */
        @FXML
        private void initialize() {
                titleColumn.setCellValueFactory(
                                cellData -> cellData.getValue().titleProperty());
                isbnColumn.setCellValueFactory(
                                cellData -> cellData.getValue().isbnProperty());
                startLoanColumn.setCellValueFactory(
                                cellData -> cellData.getValue().startLoanProperty());
                endLoanColumn.setCellValueFactory(
                                cellData -> cellData.getValue().endLoanProperty());

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
                // Add observable list data to the table
                telColumn.setCellValueFactory(
                                cellData -> {
                                        Loan loan = cellData.getValue();
                                        String tel = "";
                                        for (User user : mainApp.getUsersData())
                                                if (user.getCF().equals(loan.getCF())) {
                                                        tel = user.getTel();
                                                }
                                        return new SimpleStringProperty(tel);
                                });

                // Initialize the person table with the two columns.
                nameColumn.setCellValueFactory(
                                cellData -> {
                                        Loan loan = cellData.getValue();
                                        String tel = "";
                                        for (User user : mainApp.getUsersData())
                                                if (user.getCF().equals(loan.getCF())) {
                                                        tel = user.getName();
                                                }
                                        return new SimpleStringProperty(tel);
                                });
                surnameColumn.setCellValueFactory(
                                cellData -> {
                                        Loan loan = cellData.getValue();
                                        String tel = "";
                                        for (User user : mainApp.getUsersData())
                                                if (user.getCF().equals(loan.getCF())) {
                                                        tel = user.getSurname();
                                                }
                                        return new SimpleStringProperty(tel);
                                });

                loanTable.setItems(mainApp.getLoansData().stream()
                                .filter(loan -> !loan.isFinished())
                                .sorted((loan1, loan2) -> loan1.getEndLoan().compareTo(loan2.getEndLoan()))
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
                        ObservableList<Loan> filteredLoans = loanTable.getItems().stream()
                                        .filter(loan -> {
                                                // Ottieni i valori delle celle e controlla se contengono il testo di
                                                // ricerca
                                                return containsIgnoreCase(nameColumn.getCellData(loan), search) ||
                                                                containsIgnoreCase(surnameColumn.getCellData(loan),
                                                                                search)
                                                                ||
                                                                containsIgnoreCase(telColumn.getCellData(loan), search)
                                                                ||
                                                                containsIgnoreCase(titleColumn.getCellData(loan),
                                                                                search)
                                                                ||
                                                                containsIgnoreCase(isbnColumn.getCellData(loan),
                                                                                search);
                                        })
                                        .collect(Collectors.toCollection(FXCollections::observableArrayList));

                        // Aggiorna la TableView con i dati filtrati
                        loanTable.setItems(filteredLoans);
                } else {
                        // Se la ricerca è vuota, ripristina la lista originale
                        loanTable.setItems(mainApp.getLoansData().stream()
                                        .filter(loan -> !loan.isFinished())
                                        .sorted((loan1, loan2) -> loan1.getEndLoan().compareTo(loan2.getEndLoan()))
                                        .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                }
        }

        @FXML
        private void handlePrintPreview() {
                // Crea una nuova finestra per l'anteprima
                Stage previewStage = new Stage();
                previewStage.setTitle("Anteprima di stampa");

                // Crea un contenitore per simulare il layout di stampa
                VBox previewLayout = new VBox(10);
                previewLayout.setStyle("-fx-padding: 20; -fx-background-color: white;");

                Label header = new Label("Anteprima di stampa - Prestiti");
                header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10 0 20 0;");

                // Copia la TableView per il layout di stampa
                TableView<Loan> printTableView = new TableView<>();
                printTableView.setItems(loanTable.getItems()); // Copia i dati correnti
                printTableView.getColumns().addAll(loanTable.getColumns()); // Copia le colonne

                previewLayout.getChildren().addAll(header, printTableView);

                // Aggiungi il layout all'interfaccia della finestra
                Scene previewScene = new Scene(previewLayout, 800, 600);
                previewStage.setScene(previewScene);

                // Aggiungi un pulsante per procedere alla stampa
                Button printButton = new Button("Stampa");
                printButton.setOnAction(e -> {
                        previewStage.close();
                        printTable(printTableView);
                });
                previewLayout.getChildren().add(printButton);

                // Mostra la finestra
                previewStage.show();
        }

        private void printTable(TableView<Loan> tableView) {
                PrinterJob printerJob = PrinterJob.createPrinterJob();
                if (printerJob != null && printerJob.showPrintDialog(null)) {
                        boolean success = printerJob.printPage(tableView);
                        if (success) {
                                printerJob.endJob();
                        } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Errore di stampa");
                                alert.setHeaderText("Errore durante la stampa");
                                alert.setContentText("Non è stato possibile stampare la tabella.");
                                alert.showAndWait();
                        }
                }
        }
}
