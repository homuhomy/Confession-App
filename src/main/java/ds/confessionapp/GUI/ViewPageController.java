package ds.confessionapp.GUI;

import ds.confessionapp.adminPanel.Queue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class ViewPageController{
    public static void main(java.lang.String[] args) {

        ViewPageController v = new ViewPageController();
        v.View();

    }


    TableView<String> table;
    String v = new String();
    public void View(){
        TableColumn<String, java.lang.String> confessionColumn = new TableColumn<>("Confession");
        confessionColumn.setMaxWidth(200);
        confessionColumn.setCellValueFactory(new PropertyValueFactory<>(""));

        TableColumn<String, java.lang.String> dateColumn = new TableColumn<>("date");
        dateColumn.setMaxWidth(100);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>(""));

        table.setItems(confessionList());
        table.getColumns().addAll(confessionColumn, dateColumn);
    }
    public void start(){

    }
    public ObservableList<String> confessionList(){
        ObservableList<String> confession = FXCollections.observableArrayList();
        confession.add(ViewTable.confess.dequeue());
        return confession;
    }



    }

    class String {
        static Queue<java.lang.String> confess = new Queue<>();
        static Queue<java.lang.String> ID = new Queue<>();
        static Queue<java.lang.String> date = new Queue<>();

        public static void QueueList(){
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://34.124.213.155:3306/UMConfession_database", "root", "ds2022letsgo");
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT confession_id,file_content FROM storeConfession_table ");
                ResultSet resultSet = preparedStatement.executeQuery();


                while (resultSet.next()) {
                    ID.enqueue(resultSet.getString("confession_id"));
                    confess.enqueue(resultSet.getString("file_content"));
                    date.enqueue(resultSet.getString("creation_date"));
                }
//            System.out.println(confess.toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }
