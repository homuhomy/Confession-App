package ds.confessionapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        stage = primaryStage;
        primaryStage.setResizable(false);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartUpScreen.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Confession App");
        primaryStage.setScene(new Scene(root, 765, 480)); //width height
        primaryStage.show();

    }

    public void changeScene(String fxml) throws IOException{
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stage.getScene().setRoot(pane);
        System.out.println("test");

    }


    public static void main(String[] args) {
        launch();
    }
}