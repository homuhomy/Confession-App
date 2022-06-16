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

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class ViewPageController implements Initializable {
    static Queue<String> confess = new Queue<String>();
    static Queue<String> ID = new Queue<String>();
    static Queue<String> DATE = new Queue<String>();

    static Queue<String> test = new Queue<>();
    @FXML
    private Button backButton, update, view;

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
                DATE.enqueue(resultSet.getString("creation_date"));
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
                DATE.enqueue(resultSet.getString("creation_date"));
            }
//            System.out.println(confess.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(confess.dequeue());
    }


    @FXML
    TableView<ViewTable> table;
    @FXML
    TableColumn <ViewTable, String> confessionColumn;
    @FXML
    TableColumn <ViewTable, String> dateColumn;
    @FXML
    TableColumn <ViewTable, String> IDColumn;

    ViewTable v = new ViewTable("ID","confession","date");
    public void View(ActionEvent event){
        if(event.getSource()==view){
//            table.setItems(confessionList());
        }
    }

    public void start(ActionEvent event){

        if(event.getSource()==update) {
            table.setItems(List());
//            while(!confess.isEmpty()) {
//            table.refresh();
//            int i = 1;
//            java.util.Date date = new java.util.Date();
//            java.util.Date curr = new java.util.Date();
//                System.out.println("Date: " +date.getTime());
//                System.out.println(curr.getTime());
//
//            if (confess.getSize() <= 5) {
//                //change the numbers to minutes
//                while (!(curr.getTime() - date.getTime() > 1000 * 2 && curr.getTime() - date.getTime() <= 5 * 1000)) {
//                    curr = new java.util.Date();
//                }
//                System.out.println("1 "+curr);
//                table.setItems(confessionList());
//
//            } else if (confess.getSize() <= 10) {
//                //change the numbers to minutes
//                while (!(curr.getTime() - date.getTime() > 1000 * 2 && curr.getTime() - date.getTime() <= 5 * 1000)) {
//                    curr = new java.util.Date();
//
//                }
//                System.out.println("2 "+curr);
//                table.setItems(confessionList());
//
//            } else {
//                while (!(curr.getTime() - date.getTime() > 1000 * 2 && curr.getTime() - date.getTime() <= 8 * 1000)) {
//                    curr = new Date();
//
//                }
//                System.out.println("3 "+curr);
//                table.setItems(confessionList());
//            }
//        }
        }
    }
    public ObservableList<ViewTable> confessionList(){

        ObservableList<ViewTable> confession = FXCollections.observableArrayList();
        Timer timer = new Timer();

        if(!confess.isEmpty()){
        while(!confess.isEmpty()){
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("hi");
                    confession.add(new ViewTable(ID.dequeue(), confess.dequeue(), DATE.dequeue()));

                }
            };
                timer.scheduleAtFixedRate(task,1000,15*1000);
                if(test.getSize()<10){break;}
                return confession;
            }
        }
        else if(confess.isEmpty()){
            timer.cancel();
            QueueList();
        }

//
//        while(!confess.isEmpty()) {
////            table.refresh();
//            int i = 1;
//            java.util.Date date = new Date();
//            java.util.Date curr = new Date();
////                System.out.println("Date: " +date.getTime());
////                System.out.println(curr.getTime());
//
//            if (confess.getSize() <= 5) {
//                //change the numbers to minutes
//                while (!(curr.getTime() - date.getTime() > 1000 * 2 && curr.getTime() - date.getTime() <= 5 * 1000)) {
//                    curr = new Date();
//                }
//                System.out.println("1 "+curr);
//                confession.add(new ViewTable(ID.dequeue(), confess.dequeue(), DATE.dequeue()));
////                table.setItems(confessionList());
//
//            } else if (confess.getSize() <= 10) {
//                //change the numbers to minutes
//                while (!(curr.getTime() - date.getTime() > 1000 * 2 && curr.getTime() - date.getTime() <= 5 * 1000)) {
//                    curr = new Date();
//
//                }
//                System.out.println("2 "+curr);
//                confession.add(new ViewTable(ID.dequeue(), confess.dequeue(), DATE.dequeue()));
////                table.setItems(confessionList());
//
//            } else {
//                while (!(curr.getTime() - date.getTime() > 1000 * 2 && curr.getTime() - date.getTime() <= 8 * 1000)) {
//                    curr = new Date();
//
//                }
//                System.out.println("3 "+curr);
//                confession.add(new ViewTable(ID.dequeue(), confess.dequeue(), DATE.dequeue()));
////                table.setItems(confessionList());
//
//            }
//
//        }
////        confession.add(new ViewTable(ID.dequeue(), confess.dequeue(), DATE.dequeue()));

        return confession;
    }

    public ObservableList<ViewTable> List() {

        ObservableList<ViewTable> list = FXCollections.observableArrayList();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = DatabaseConnection.getConnection();
        String searchViewQuery = "SELECT confession_id,file_content, creation_date FROM storeConfession_table";

        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet queryOutput = statement.executeQuery(searchViewQuery);

            while (queryOutput.next()) {

                String queryConfessionId = queryOutput.getString("confession_id");
                String queryFileContent = queryOutput.getString("file_content");
                String queryCreationDate = queryOutput.getString("creation_date");
                while(!confess.isEmpty()) {
//            java.util.Date date = new java.util.Date();
//            java.util.Date curr = new java.util.Date();
//                System.out.println("Date: " +date.getTime());
//                System.out.println(curr.getTime());
//
//            if (confess.getSize() <= 5) {
//                //change the numbers to minutes
//                while (!(curr.getTime() - date.getTime() > 1000 * 2 && curr.getTime() - date.getTime() <= 5 * 1000)) {
//                    curr = new java.util.Date();
//                }
//                System.out.println("1 "+curr);
//                table.setItems(List());
//
//            } else if (confess.getSize() <= 10) {
//                //change the numbers to minutes
//                while (!(curr.getTime() - date.getTime() > 1000 * 2 && curr.getTime() - date.getTime() <= 5 * 1000)) {
//                    curr = new java.util.Date();
//
//                }
//                System.out.println("2 "+curr);
//                table.setItems(confessionList());
//
//            } else {
//                while (!(curr.getTime() - date.getTime() > 1000 * 2 && curr.getTime() - date.getTime() <= 8 * 1000)) {
//                    curr = new Date();
//
//                }
//                System.out.println("3 "+curr);
//                table.setItems(confessionList());
//            }
        }
                list.add(new ViewTable(queryConfessionId,queryFileContent,queryCreationDate));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    return list;
    }

        public void timer(){
        Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    count++;
                    System.out.println("hi");
                    confessionList();
                }
            };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        confessionColumn = new TableColumn<>("Confession");
        confessionColumn.setMaxWidth(200);

//        dateColumn = new TableColumn<>("Date");
        dateColumn.setMaxWidth(100);
//        dateColumn.setCellValueFactory(new PropertyValueFactory<>(DATE.dequeue()));

//        IDColumn = new TableColumn<>("ID");
        IDColumn.setMaxWidth(300);
//        IDColumn.setCellValueFactory(new PropertyValueFactory<>());


            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://database-student-confession-aws.canrsxzrd6mg.us-west-1.rds.amazonaws.com:3306/UMCP_Database2", "root", "ds2022letsgo");
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT confession_id,file_content,creation_date FROM storeConfession_table ");
                ResultSet resultSet = preparedStatement.executeQuery();


                while (resultSet.next()) {
                    ID.enqueue(resultSet.getString("confession_id"));
                    confess.enqueue(resultSet.getString("file_content"));
                    DATE.enqueue(resultSet.getString("creation_date"));
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
}

