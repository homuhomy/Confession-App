
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;


public class JavaFXFileChooser extends Application {
    
    @Override
    public void static (Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(Scene);
        stage.show();
    }
    
    public static void main(String[] args) {

        launch(args);
    }
}
