package ds.confessionapp.GUI;

import ds.confessionapp.adminPanel.Queue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

import java.sql.*;

public class ViewTable {
    static Queue<String> confess = new Queue<>();
    static Queue<String> ID = new Queue<>();
    static Queue<String> date = new Queue<>();

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
