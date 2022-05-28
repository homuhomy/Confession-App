package ds.confessionapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        stage = primaryStage;
        primaryStage.setResizable(false);

        Parent root = FXMLLoader.load(getClass().getResource("StartUpScreen.fxml"));

        primaryStage.setTitle("Confession App");
        primaryStage.setScene(new Scene(root, 765, 480)); //width height
        primaryStage.show();

//        primaryStage.setOnCloseRequest(event -> {
//            try {
//                userLogOut(primaryStage);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
    }

    public void changeScene(String fxml) throws IOException{
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stage.getScene().setRoot(pane);
        System.out.println("test");
    }

//    public void userLogOut(Stage primaryStage) throws IOException{
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Close");
//        alert.setHeaderText("You're about to close the windows");
//        alert.setContentText("Do you want to save before exiting?");
//
//        if (alert.showAndWait().get() == ButtonType.OK){
//            Main main = new Main();
//            main.changeScene("LoginForm.fxml");
//            System.out.println("Successfully logged out");
//        }
//
//    }

    public static void main(String[] args) {
        launch();
    }
}