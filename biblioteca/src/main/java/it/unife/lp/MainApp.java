package it.unife.lp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import it.unife.lp.model.Book;
import it.unife.lp.model.Loan;
import it.unife.lp.model.User;
import it.unife.lp.view.RootLayoutController;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<Book> booksData = FXCollections.observableArrayList();
    private ObservableList<User> usersData = FXCollections.observableArrayList();
    private ObservableList<Loan> loansData = FXCollections.observableArrayList();

    public ObservableList<Book> getBooksData() {
        return booksData;
    }

    public ObservableList<Loan> getLoansData() {
        return loansData;
    }

    public ObservableList<User> getUsersData() {
        return usersData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gestionale Biblioteca...");

        String workingDir = System.getProperty("user.dir");
        File dataDir = new File(workingDir + File.separator + "data");
        // se dir data non esiste allora la creo e inserisco i file json vuoti
        if (!dataDir.exists()) {
            if (dataDir.mkdirs()) {
                System.out.println("Directory 'data' creata con successo.");
                try {
                    createAndInitializeJsonFile(new File(dataDir, "book.json"), List.of());
                    createAndInitializeJsonFile(new File(dataDir, "user.json"), List.of());
                    createAndInitializeJsonFile(new File(dataDir, "loan.json"), List.of());
                } catch (IOException e) {
                    System.err.println("Errore nella creazione dei file: " + e.getMessage());
                }
            } else {
                System.err.println("Errore nella creazione della directory 'data'.");
            }
        }

        // la directory data esiste già allora
        booksData = loadDataFromFile(new File(dataDir, "book.json"), Book[].class);
        usersData = loadDataFromFile(new File(dataDir, "user.json"), User[].class);
        loansData = loadDataFromFile(new File(dataDir, "loan.json"), Loan[].class);

        initRootLayout();
        // showPersonOverview();
    }

    private <T> ObservableList<T> loadDataFromFile(File file, Class<T[]> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            T[] data = mapper.readValue(file, clazz);
            return FXCollections.observableArrayList(data);
        } catch (IOException e) {
            System.err.println("Errore nel caricamento dei dati dal file " + file.getName());
            return FXCollections.observableArrayList();
        }
    }

    private void createAndInitializeJsonFile(File file, Object initialData) throws IOException {
        if (!file.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            mapper.registerModule(new JavaTimeModule());
            mapper.writeValue(file, initialData);
            System.out.println("File '" + file.getName() + "' creato e inizializzato con dati di esempio.");
        } else {
            System.out.println("File '" + file.getName() + "' esiste già.");
        }
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Errore nel caricamento del layout principale.");
        }
    }

    public void showUser() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/UserOverview.fxml"));
            AnchorPane userOverview = (AnchorPane) loader.load();
            // Set person overview into the center of root layout.
            rootLayout.setCenter(userOverview);
            // Give the controller access to the main app.
            // PersonOverviewController controller = loader.getController();
            // controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showBook() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BookOverview.fxml"));
            AnchorPane bookOverview = (AnchorPane) loader.load();
            // Set person overview into the center of root layout.
            rootLayout.setCenter(bookOverview);
            // Give the controller access to the main app.
            // PersonOverviewController controller = loader.getController();
            // controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLoan() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LoanOverview.fxml"));
            AnchorPane LoanOverview = (AnchorPane) loader.load();
            // Set person overview into the center of root layout.
            rootLayout.setCenter(LoanOverview);
            // Give the controller access to the main app.
            // PersonOverviewController controller = loader.getController();
            // controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}