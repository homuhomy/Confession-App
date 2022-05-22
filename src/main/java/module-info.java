module ds.confessionapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens ds.confessionapp to javafx.fxml;
    exports ds.confessionapp;
}