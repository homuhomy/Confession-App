//package ds.confessionapp;
//
//import ds.confessionapp.adminPanel.DatabaseConnection;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.collections.transformation.FilteredList;
//import javafx.collections.transformation.SortedList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.input.KeyCode;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//
//import javax.sound.sampled.Clip;
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ResourceBundle;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import static ds.confessionapp.newMusic.clip;
//
//public class SearchPageController implements Initializable {
//
//    @FXML
//    private TableColumn<ConfessionSearchModel, String> ConfessionColumn;
//    @FXML
//    private TableColumn<ConfessionSearchModel, String> DateColumn;
//    @FXML
//    private TableColumn<ConfessionSearchModel, String> ReplyIdColumn;
//    @FXML
//    private TableView<ConfessionSearchModel> TableView;
//    @FXML
//    private TableColumn<ConfessionSearchModel, String> ConfessionIdColumn;
//    @FXML
//    private TextField searchTextField;
//    @FXML
//    private Button backButton;
//    @FXML
//    private Button homeButtonIcon, submitButtonIcon;
//
//    ObservableList<ConfessionSearchModel> confessionSearchModelObservableList = FXCollections.observableArrayList();
//
//    @Override
//    public void initialize(URL url, ResourceBundle resource) {
//        DatabaseConnection connectNow = new DatabaseConnection();
//        Connection connection = DatabaseConnection.getConnection();
//        String searchViewQuery = "SELECT confession_id,file_content,reply_id, creation_date FROM storeConfession_table";
//
//         try {
//                Statement statement = DatabaseConnection.getConnection().createStatement();
//                ResultSet queryOutput = statement.executeQuery(searchViewQuery);
//
//                while(queryOutput.next()) {
//
//                String queryConfessionId = queryOutput.getString("confession_id");
//                String queryFileContent = queryOutput.getString("file_content");
//                String queryReplyId = queryOutput.getString("reply_id");
//                String queryCreationDate = queryOutput.getString("creation_date");
//
//                //populate observable list
//                confessionSearchModelObservableList.add(new ConfessionSearchModel(queryConfessionId, queryFileContent, queryReplyId, queryCreationDate));
//            }
//
//            ConfessionIdColumn.setCellValueFactory(new PropertyValueFactory<>("confession_id"));
//            //ConfessionIdColumn.setStyle( "-fx-alignment: CENTER;");
//            ConfessionColumn.setCellValueFactory(new PropertyValueFactory<>("file_content"));
//            ConfessionColumn.setCellFactory (col -> {
//                TableCell<ConfessionSearchModel, String> cell = new TableCell<>() {
//                    @Override
//                    public void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (item != null) {
//                            Text text = new Text(item);
//                            text.setStyle(  " -fx-text-wrap: true;" +
//                                    " -fx-text-alignment:left;");
//                            text.setWrappingWidth(col.getPrefWidth() - 35);
//                            this.setPrefHeight(text.getLayoutBounds().getHeight() + 10);
//                            this.setGraphic(text);
//                        }
//                    }
//                };
//                return cell;
//            });
//
//            ReplyIdColumn.setCellValueFactory(new PropertyValueFactory<>("reply_id"));
//            DateColumn.setCellValueFactory(new PropertyValueFactory<>("creation_date"));
//            TableView.setItems(confessionSearchModelObservableList);
//            //initial filtered list
//            FilteredList<ConfessionSearchModel> filteredData = new FilteredList<>(confessionSearchModelObservableList, b -> true);
//            searchTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
//                filteredData.setPredicate(confessionSearchModel -> {
//                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
//                        return true;
//                    }
//                    String searchKeyword = newValue.toLowerCase();
//                    if (confessionSearchModel.getConfession_id().toLowerCase().indexOf(searchKeyword) > -1) {
//                        return true; //found a match for confession_id
//                    } else if (confessionSearchModel.getFile_content().toLowerCase().indexOf(searchKeyword) > -1) {
//                        return true; //found a match for confession_id
//                    } else if (confessionSearchModel.getReply_id().toLowerCase().indexOf(searchKeyword) > -1) {
//                        return true; //found a match for confession_id
//                    } else if (confessionSearchModel.getCreation_date().toLowerCase().indexOf(searchKeyword) > -1) {
//                        return true; //found a match for confession_id
//                    } else
//                        return false; //no match found
//                });
//            });
//
//            SortedList<ConfessionSearchModel> sortedData = new SortedList<>(filteredData);
//
//            //Bind sorted result with Table View
//            sortedData.comparatorProperty().bind(TableView.comparatorProperty());
//
//            //apply filtered and sorted data to the table view
//            TableView.setItems(sortedData);
//
//        } catch (SQLException e) {
//            Logger.getLogger(SearchPageController.class.getName()).log(Level.SEVERE, null, e);
//        }
//    }
//    //TableColumn<ConfessionSearchModel, String> ConfessionColumn
//
//
//
//    public void switchScenes(ActionEvent event) throws Exception {
//        Stage stage = null;
//        Parent root = null;
////        System.out.println(event.getSource());
//
//        if (event.getSource() == backButton || event.getSource()==homeButtonIcon) {
//            stage = (Stage) backButton.getScene().getWindow();
//            root = FXMLLoader.load(getClass().getResource("StartUpScreen.fxml"));
//        }
//        else if (event.getSource()==submitButtonIcon) {
//            stage = (Stage) submitButtonIcon.getScene().getWindow();
//            root = FXMLLoader.load(getClass().getResource("submitConfession.fxml"));
//        }
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//
//    }
//
//    public void stopMusic(){
//        clip.stop();
//    }
//
//    public void continueMusic() {
//        clip.start();
//        clip.loop(Clip.LOOP_CONTINUOUSLY);
//    }
//}