package ds.confessionapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;

import javax.sound.sampled.Clip;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import static ds.confessionapp.newMusic.clip;

public class submittedPageController implements Initializable {

    @FXML
    private Label st;

    @FXML
    private Button submitButton;

    @FXML
    private Label time;

    @FXML
    private Button viewButton;

    public void initialize(URL url, ResourceBundle resource){
        String s = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss a", Locale.ENGLISH)
                .withZone(ZoneId.systemDefault())
                .format(Instant.now());
        System.out.println("Creation Time: " + s); // yyyy-mm-dd 11:22:32
        this.st = st;
        st.setText(s);
        st.setVisible(true);
    }

    public void switchScenes(ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;
//        System.out.println(event.getSource());
        if (event.getSource() == submitButton) {
            stage = (Stage) submitButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("submitConfession.fxml"));
        }
        else if (event.getSource()==viewButton) {
            stage = (Stage) viewButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("viewConfessionPage.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void stopMusic(){
        clip.stop();
    }

    public void continueMusic() {
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
