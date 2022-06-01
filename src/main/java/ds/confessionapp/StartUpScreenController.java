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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Scanner;

public class StartUpScreenController implements Initializable {


    @FXML
    public Button ok, submitButton, viewButton, backForsubmitpage, backforviewpage, login, admin, backForadmin, backforAdminPanel;
    public TextField input, pswdinput;

    public Label XsuccessLabel;

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

        else if(event.getSource()==login){
            stage = (Stage) login.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
        }

        else if(event.getSource()==ok){
            stage = (Stage) ok.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
        }

        else if(event.getSource()==backforAdminPanel){
            stage = (Stage) backforAdminPanel.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("StartUpScreen.fxml"));
        }


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public void Success(){
        XsuccessLabel.setVisible(true);
        XsuccessLabel.setText("Login Successful!\nPress 'OK' to continue ");
        ok.setVisible(true);
    }
    @FXML
    public void Xsuccess(){
        XsuccessLabel.setText("Login Unsuccessful!\nPlease try again");
        ok.setVisible(false);
    }
    @FXML

    public void loginAction(ActionEvent event){
        int ans = verify(input.getText(),pswdinput.getText());
        if(ans==1){

            Success();

        }
        else
           Xsuccess();
//            System.exit(0);
    }

    public int verify(String username, String pswd){
        int found = 0; //false

        try{
            //Path dir = Paths.get("admin/AdminCredentials");
            Scanner read = new Scanner(new FileInputStream("admin/AdminCredentials"));
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
