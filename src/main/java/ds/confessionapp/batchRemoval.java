package ds.confessionapp;

import java.sql.*;

public class batchRemoval {

    public static boolean remove(){

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://database-student-confession-aws.canrsxzrd6mg.us-west-1.rds.amazonaws.com:3306/UMCP_Database2", "root", "ds2022letsgo");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT confession_id,file_content,creation_date FROM storeConfession_table ");
            ResultSet resultSet = preparedStatement.executeQuery();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
