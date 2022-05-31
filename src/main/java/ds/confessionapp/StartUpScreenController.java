package ds.confessionapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class StartUpScreenController implements Initializable {


    @FXML
    public Button submitButton, viewButton, backForsubmitpage, backforviewpage, admin, backForadmin;
    public TextField input, pswdinput;
    public void switchScenes(ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;
        System.out.println(event.getSource());

        if(event.getSource()== submitButton){
            stage = (Stage) submitButton.getScene().getWindow();
             root = FXMLLoader.load(getClass().getResource("submitConfession.fxml"));

        }
        else if(event.getSource()==viewButton){
            stage = (Stage) viewButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("ViewConfessionPage.fxml"));

        }

        else if(event.getSource()==backForsubmitpage){
            stage = (Stage) backForsubmitpage.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("StartUpScreen.fxml"));
        }

        else if(event.getSource()==backforviewpage){
            stage = (Stage) backforviewpage.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("StartUpScreen.fxml"));
        }

        else if(event.getSource()==backForadmin){
            stage = (Stage) backForadmin.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("StartUpScreen.fxml"));
        }

        else if(event.getSource()==admin){
            stage = (Stage) admin.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
        }



        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    public void loginAction(ActionEvent event){
        int ans = verify(input.getText(),pswdinput.getText());
        if(ans==1){
            System.out.println("Success");;
        }
        else
            System.out.println("not Successful");
//            System.exit(0);
    }

    public int verify(String username, String pswd){
        int found = 0; //false

        try{
            Scanner read = new Scanner(new FileInputStream("AdminCredentials"));
            while(read.hasNextLine()){
                if(username.equals(read.nextLine())){
                    if(pswd.equals(read.nextLine())){
                        found = 1;  //true
                    }
                }
            }
            read.close();
            System.out.println(found);
        }catch(Exception e){
            System.out.println("file not found - at verify method");
        }
        return found;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
