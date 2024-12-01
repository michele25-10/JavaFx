package it.unife.lp.view;

import javafx.fxml.FXML;
import it.unife.lp.MainApp;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 *
 * @author Michele Gabrieli
 */
public class RootLayoutController {
    // Reference to the main application
    private MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     * s
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleUser() {
        System.out.println("Click handle user");
        mainApp.showUser();
    }

    @FXML
    private void handleBook() {
        System.out.println("Click handle Book");

        mainApp.showBook();
    }

    @FXML
    private void handleLoan() {
        System.out.println("Click handle Loan");

        mainApp.showLoan();
    }

}
