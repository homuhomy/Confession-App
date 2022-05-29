package ds.confessionapp.adminPanel;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class DatabaseRetrieveData {
    public static void main(String[] args) throws SQLException {

        String SQL="SELECT *FROM storeConfession_table";
        try(Connection connection = DatabaseCheck.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQL); ResultSet
                    rs = ps.executeQuery()) {
            System.out.println("Following flies are downloaded from database..");
            while (rs.next()) {
                String confessionId = rs.getString("confession_id");
                String replyId = rs.getString("reply_id");
                String creationDate = rs.getString("creation_date");

                System.out.println("Confession ID: "+ confessionId);
                System.out.println("Reply ID: "+replyId);
                System.out.println("Confession Creation Date: "+ creationDate);
                Clob clob = rs.getClob("file_content");
                InputStream inputStream = clob.getAsciiStream();

                System.out.println("-----------------------------------");
                //put the retrieved files to the folder
                Files.copy(inputStream, Paths.get("RetrievedFilesFromDB/"+ confessionId));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
