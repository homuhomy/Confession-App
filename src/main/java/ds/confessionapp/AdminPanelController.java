package ds.confessionapp;

import ds.confessionapp.adminPanel.JDBCDelete;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.sound.sampled.Clip;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ds.confessionapp.newMusic.clip;

public class AdminPanelController implements Initializable {

    @FXML
    private Button Back, Delete, View;

    @FXML
    private TextField textField;
    @FXML
    TableView<ConfessionSearchModel> table;
    @FXML
    TableColumn<ConfessionSearchModel, String> confessionCol;
    @FXML
    TableColumn<ConfessionSearchModel, String> idCol;
    @FXML
    TableColumn<ConfessionSearchModel, String> replyCol;
    @FXML
    TableColumn<ConfessionSearchModel, String> dateCol;

   static String ID, content, replyID, date;

    public static void main(String[] args) {

        AdminPanelController ad = new AdminPanelController();
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://database-student-confession-aws.canrsxzrd6mg.us-west-1.rds.amazonaws.com:3306/UMCP_Database2", "root", "ds2022letsgo");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT confession_id,file_content,reply_id,creation_date FROM storeConfession_table ");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                ID = resultSet.getString("confession_id");
                content = resultSet.getString("file_content");
                replyID = resultSet.getString("reply_id");
                date = resultSet.getString("creation_date");

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    ObservableList<ConfessionSearchModel> AdminList = FXCollections.observableArrayList();
    public ObservableList<ConfessionSearchModel> LIST(){
//        AdminList.add(new ConfessionSearchModel(ID,content,replyID,date));

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://database-student-confession-aws.canrsxzrd6mg.us-west-1.rds.amazonaws.com:3306/UMCP_Database2", "root", "ds2022letsgo");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT confession_id,file_content,reply_id,creation_date FROM storeConfession_table ");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                ID = resultSet.getString("confession_id");
                content = resultSet.getString("file_content");
                replyID = resultSet.getString("reply_id");
                date = resultSet.getString("creation_date");

                AdminList.add(new ConfessionSearchModel(ID,content,replyID,date));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        table.setItems(AdminList);
    return AdminList;
    }

    public void switchScenes(ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;

        if (event.getSource() == Back) {
            stage = (Stage) Back.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("StartUpScreen.fxml"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LIST();

        confessionCol.setCellValueFactory(new PropertyValueFactory<ConfessionSearchModel, String>("file_content"));
        idCol.setCellValueFactory(new PropertyValueFactory<ConfessionSearchModel, String>("confession_id"));
        dateCol.setCellValueFactory(new PropertyValueFactory<ConfessionSearchModel, String >("creation_date"));
        replyCol.setCellValueFactory(new PropertyValueFactory<>("reply_id"));

        table.setItems(AdminList);
    }


    public void View(ActionEvent event){
        if(event.getSource()==View){
            table.getItems().clear();
            LIST();
            table.setItems(AdminList);
        }
    }
    public void delete(ActionEvent event){
        String text = textField.getText();
        loop(text);
    }

    public boolean checkDependantPost(String postId){

        boolean hasNextPost;
        String dependantPostId;

        try {
            Connection ConnectDB = DriverManager.getConnection("jdbc:mysql://database-student-confession-aws.canrsxzrd6mg.us-west-1.rds.amazonaws.com:3306/UMCP_Database2", "root", "ds2022letsgo");
            // post Id here is reply posts Id
            String SQL = "SELECT * FROM storeConfession_table WHERE reply_id LIKE'%"+postId+"%'";
            Statement stmt_2 = ConnectDB.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs_2 = stmt_2.executeQuery(SQL);

            hasNextPost = rs_2.next();
            if(!hasNextPost){
                deletePost(postId);
                return false;
            } else {
                while(hasNextPost){
                    dependantPostId = rs_2.getString("confession_id");
                    if (checkDependantPost(dependantPostId))    // if posts is not a leaf
                        deletePost(dependantPostId);
                    hasNextPost = rs_2.next();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminPanelController.class.getName());
        }
        return true;
    }

    public void deletePost(String postId){
        try{
            Connection ConnectDB = DriverManager.getConnection("jdbc:mysql://database-student-confession-aws.canrsxzrd6mg.us-west-1.rds.amazonaws.com:3306/UMCP_Database2", "root", "ds2022letsgo");

            Statement stmt_3 = ConnectDB.createStatement();
            String SQL_DELETE = "DELETE FROM storeConfession_table WHERE confession_id LIKE'%"+postId+"%'";
            stmt_3.executeUpdate(SQL_DELETE);
            System.out.println("Post "+ postId +" deleted successfully");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }


    public void loop(String postId){
        try{
            Connection ConnectDB = DriverManager.getConnection("jdbc:mysql://database-student-confession-aws.canrsxzrd6mg.us-west-1.rds.amazonaws.com:3306/UMCP_Database2", "root", "ds2022letsgo");

            String SQL_REPLYPOST = "SELECT * FROM storeConfession_table WHERE reply_id LIKE'%"+postId+"%'";

            Statement stmt = ConnectDB.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(SQL_REPLYPOST);
            boolean hasNextPost = rs.next();
            String replyPostId;

            if(hasNextPost){  // reply posts exists
                while(hasNextPost){
                    replyPostId = rs.getString("confession_id");
                    checkDependantPost(replyPostId);   // check if reply posts has dependant post
                    deletePost(replyPostId);           // delete current reply post
                    hasNextPost = rs.next();
                }
            }
            deletePost(postId);     // delete main post

            stmt.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}

