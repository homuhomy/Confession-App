package ds.confessionapp.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

public class StartUpScreenController implements Initializable {


    @FXML
    public Button submitButton, viewButton;
    public void switc(ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;
        System.out.println(event.getSource());

        if(event.getSource()== submitButton){
            stage = (Stage) submitButton.getScene().getWindow();
             root = FXMLLoader.load(getClass().getResource("submitConfession.fxml"));

        }
        else if(event.getSource()==viewButton){
            stage = (Stage) viewButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("viewConfession.fxml"));
        }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
