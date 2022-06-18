package ds.confessionapp;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AdminPanelController implements Initializable {

    @FXML
    private Button Back, Delete;

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
        System.out.println(ad.check("#UM0012"));
        System.out.println(ad.check("#UM0001"));
        System.out.println(ad.check("#UM0002"));
        System.out.println(ad.check("#UM0003"));
        System.out.println(ad.check("#UM0004"));
        System.out.println(ID);
        System.out.println(content);
        System.out.println(replyID);
        System.out.println(date);
    }
    ObservableList<ConfessionSearchModel> AdminList = FXCollections.observableArrayList();
    public ObservableList<ConfessionSearchModel> LIST(){
//        AdminList.add(new ConfessionSearchModel(ID,content,replyID,date));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        confessionCol.setCellValueFactory(new PropertyValueFactory<ConfessionSearchModel, String>("file_content"));
        idCol.setCellValueFactory(new PropertyValueFactory<ConfessionSearchModel, String>("confession_id"));
        dateCol.setCellValueFactory(new PropertyValueFactory<ConfessionSearchModel, String >("creation_date"));
        replyCol.setCellValueFactory(new PropertyValueFactory<>("reply_id"));

        table.setItems(AdminList);
    }

//    public String delete(ActionEvent event){
//        int selectedID = table.getSelectionModel().getSelectedIndex();
//        ConfessionSearchModel cs = new ConfessionSearchModel("","","","");
//
//        cs = table.getItems().remove(selectedID);
//        table.setItems(table.getItems());
//
//    return cs.reply_id;
//    }
    public void remov(ActionEvent event){
        int selectedID = table.getSelectionModel().getSelectedIndex();
        ConfessionSearchModel cs = new ConfessionSearchModel("","","","");

        cs = table.getItems().remove(selectedID);

        ConfessionSearchModel se = new ConfessionSearchModel("","",replyID.substring(0,3),"");

        String reply = cs.reply_id;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://database-student-confession-aws.canrsxzrd6mg.us-west-1.rds.amazonaws.com:3306/UMCP_Database2", "root", "ds2022letsgo");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT confession_id,file_content,reply_id,creation_date FROM storeConfession_table ");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                String rplyID = resultSet.getString("reply_id");
                String confessID = resultSet.getString("confession_id");
                String file = resultSet.getString("file_content");
                String dat = resultSet.getString("creation_date");
                ConfessionSearchModel dl = new ConfessionSearchModel(file,confessID,rplyID,dat);
                if (reply.equals("")) {
                    System.out.println("doesnt have reply_id");
                } else if(reply.equals(confessID)) {
                        //CONTINUE FROM HEREEEE!!!!!!
                        dl = new ConfessionSearchModel(file,confessID,rplyID,dat);

                    System.out.println(reply);
                    System.out.println("rplyID "+rplyID);
                    System.out.println("have reply-id");

                    System.out.println(dl.reply_id);
                    System.out.println(dl.file_content);
                    System.out.println(table.getItems().remove(dl));
                    System.out.println(rplyID);
                    table.getItems().remove(dl);
                    table.setItems(table.getItems());
                }
                else if(!(reply.equals(confessID))){

                    continue;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    public boolean check(String reply){
        if(replyID.equals(reply)){
            return true;
        }
        else return false;
    }


}

