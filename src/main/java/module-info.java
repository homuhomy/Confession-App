module ds.confessionapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens ds.confessionapp to javafx.fxml;
    exports ds.confessionapp;
}