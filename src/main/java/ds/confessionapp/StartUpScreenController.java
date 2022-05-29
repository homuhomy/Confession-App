package ds.confessionapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.io.IOException;

public class StartUpScreenController {

    Stage stage;
    Parent root;
    @FXML
    Button btn = new Button();
    public void switc(ActionEvent event) throws IOException {
        System.out.println(event.getSource());
        Parent root = FXMLLoader.load(getClass().getResource("submitConfessions.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

//            stage = (Stage) btn.getScene().getWindow();
//            root = FXMLLoader.load(getClass().getResource("MovieSelection.fxml"));
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
    }

    private Label Text;

    public void onClick(){
        Text.setText("Works");
    }

}
