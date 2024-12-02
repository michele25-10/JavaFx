package it.unife.lp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
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
import it.unife.lp.util.JsonController;
import it.unife.lp.view.BookOverviewController;
import it.unife.lp.view.RootLayoutController;
import it.unife.lp.view.UserOverviewController;
import it.unife.lp.view.UserEditDialogController;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    public static File dataDir = new File(System.getProperty("user.dir") + File.separator + "data");

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

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gestionale Biblioteca...");

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
        booksData = JsonController.loadData(new File(dataDir, "book.json"), Book.class);
        usersData = JsonController.loadData(new File(dataDir, "user.json"), User.class);
        loansData = JsonController.loadData(new File(dataDir, "loan.json"), Loan.class);

        System.out.println(usersData);

        initRootLayout();
        showUser();
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
            UserOverviewController controller = loader.getController();
            controller.setMainApp(this);
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
            BookOverviewController controller = loader.getController();
            controller.setMainApp(this);
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

    public boolean showUserEditDialog(User user) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/userEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            UserEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUser(user);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        launch();
    }

}