package ds.confessionapp;

import ds.confessionapp.adminPanel.DatabaseConnection;
import ds.confessionapp.adminPanel.Queue;
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

import javax.sound.sampled.Clip;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import static ds.confessionapp.newMusic.clip;

public class ViewPageController implements Initializable {
    static Queue<String> confess = new Queue<String>();
    static Queue<String> ID = new Queue<String>();
    static Queue<Date> DATE = new Queue<>();

    static Queue<String> test = new Queue<>();
    @FXML
    private Button backButton, update, view, Mute, Unmute;

    private int count =0;
    public static void QueueList() {

        //queues everything in the Queue
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://database-student-confession-aws.canrsxzrd6mg.us-west-1.rds.amazonaws.com:3306/UMCP_Database2", "root", "ds2022letsgo");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT confession_id,file_content,creation_date FROM storeConfession_table ");
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                ID.enqueue(resultSet.getString("confession_id"));
                confess.enqueue(resultSet.getString("file_content"));
                DATE.enqueue(resultSet.getDate("creation_date"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://database-student-confession-aws.canrsxzrd6mg.us-west-1.rds.amazonaws.com:3306/UMCP_Database2", "root", "ds2022letsgo");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT confession_id,file_content,creation_date FROM storeConfession_table ");
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                ID.enqueue(resultSet.getString("confession_id"));
                confess.enqueue(resultSet.getString("file_content"));
                DATE.enqueue(resultSet.getTimestamp("creation_date"));

//                System.out.println(confess.dequeue());
//                System.out.println(ID.dequeue());
//                System.out.println(DATE.dequeue());
            }
//            System.out.println(confess.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    TableView<ViewTable> table;
    @FXML
    TableColumn <ViewTable, String> confessionColumn;
    @FXML
    TableColumn <ViewTable, String> dateColumn;
    @FXML
    TableColumn <ViewTable, String> IDColumn;

//    ViewTable v = new ViewTable("ID","confession","date");
    public void View(ActionEvent event){
        if(event.getSource()==view){
            table.setItems(confessionList());
        }
    }

    public void start(ActionEvent event){

        if(event.getSource()==update) {
            table.refresh();
            table.setItems(confessionList());
        }
    }
    public ObservableList<ViewTable> confessionList(){

        ObservableList<ViewTable> confession = FXCollections.observableArrayList();

        if(!confess.isEmpty()){
        while(!confess.isEmpty()){
            Date date = DATE.peek();
            Date curr = new Date();
            if (confess.getSize() <= 5) {
                //change the numbers to minutes
                while (!(curr.getTime()-date.getTime()>=15*60*1000)) {
                    curr = new Date();
                }
                confession.add(new ViewTable(ID.dequeue(), confess.dequeue(), (java.sql.Date) DATE.dequeue()));
                System.out.println(1);
                table.setItems(confession);
            }

            else if (confess.getSize() <= 10) {
                //change the numbers to minutes
                while (!(curr.getTime()-date.getTime()>=10*60*1000)) {
                    curr = new Date();
                }
                confession.add(new ViewTable(ID.dequeue(), confess.dequeue(), (java.sql.Date) DATE.dequeue()));
                System.out.println(2);
                table.setItems(confession);
            }

            else {
                //change the numbers to minutes
                while (!(curr.getTime()-date.getTime()>=5*60*1000)) {
                    curr = new Date();
                }
                confession.add(new ViewTable(ID.dequeue(), confess.dequeue(), (java.sql.Date) DATE.dequeue()));
                System.out.println(3);
                table.setItems(confession);
            }
        }
        }
        else if(confess.isEmpty()){

            QueueList();
        }

        return confession;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        confessionColumn.setMaxWidth(200);

        dateColumn.setMaxWidth(100);

        IDColumn.setMaxWidth(300);

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://database-student-confession-aws.canrsxzrd6mg.us-west-1.rds.amazonaws.com:3306/UMCP_Database2", "root", "ds2022letsgo");
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT confession_id,file_content,creation_date FROM storeConfession_table ");
                ResultSet resultSet = preparedStatement.executeQuery();


                while (resultSet.next()) {
                    ID.enqueue(resultSet.getString("confession_id"));
                    confess.enqueue(resultSet.getString("file_content"));
                    DATE.enqueue(resultSet.getDate("creation_date"));
                    test.enqueue(" ");
                }
//            System.out.println(confess.toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
//        table.getColumns().addAll(confessionColumn,IDColumn, dateColumn);
        confessionColumn.setCellValueFactory(new PropertyValueFactory<>("file_content"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("creation_date"));
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("confession_id"));

        confessionList();
    }

    public void switchScenes(ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;
//        System.out.println(event.getSource());

        if (event.getSource() == backButton) {
            stage = (Stage) backButton.getScene().getWindow();
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
}

