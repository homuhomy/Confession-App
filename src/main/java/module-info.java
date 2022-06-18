module ds.confessionapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens ds.confessionapp to javafx.fxml;
    exports ds.confessionapp;
    exports ds.confessionapp.adminPanel;
    opens ds.confessionapp.adminPanel to javafx.fxml;
}