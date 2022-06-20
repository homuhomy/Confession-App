module ds.confessionapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.compiler;


    opens ds.confessionapp to javafx.fxml;
    exports ds.confessionapp;
    exports ds.confessionapp.adminPanel;
    opens ds.confessionapp.adminPanel to javafx.fxml;
}