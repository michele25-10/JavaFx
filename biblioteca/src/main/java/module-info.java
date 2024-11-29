module it.unife.lp {
    requires javafx.controls;
    requires javafx.fxml;

    opens it.unife.lp to javafx.fxml;

    exports it.unife.lp;
}
