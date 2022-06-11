
import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private AnchorPane anchorpane;
    
    //button no 1
    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser chooseFile = new FileChooser();
        chooseFile.setTitle("Open File");
        Stage stage = (Stage)anchorpane.getScene().getWindow();
        
        File file = chooseFile.showOpenDialog(stage);
        
        if(file!=null) {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        }
    }
 
    //button no 2
    @FXML
    private void multiFileChooserAction(ActionEvent event) {
        FileChooser chooseFile = new FileChooser();
        chooseFile.setTitle("Open Multiple Files");
        Stage stage = (Stage)anchorpane.getScene().getWindow();
        
        List<File> list = chooseFile.showOpenMultipleDialog(stage);
        
        if(list !=null) {
            for(File file : list) {
            OnlyStage desktop = OnlyStage.getDesktop();
            desktop.open(file);
        }
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    }
}
