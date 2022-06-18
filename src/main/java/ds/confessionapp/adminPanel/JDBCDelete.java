package ds.confessionapp.adminPanel;

import ds.confessionapp.ConfessionSearchModel;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.sql.*;
import java.util.Scanner;

public class JDBCDelete {
    public static void main(String[] args) {
        //declare resources
        Statement statement = null;
        Scanner scanner = null;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = DatabaseConnection.getConnection();
        //unlink query

        try{
            //prepare statement
            statement = DatabaseConnection.getConnection().createStatement();
            scanner = new Scanner(System.in);

            System.out.println("Enter the confession id that will be deleted: ");
            String deleteConfessionId = scanner.nextLine();
            //String deleteFromMainId = "DELETE FROM storeConfession_table WHERE confession_id = ?";
            String selectQuery = "DELETE FROM storeConfession_table WHERE confession_id = ? OR reply_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT confession_id,file_content,reply_id,creation_date FROM storeConfession_table ");
            ResultSet resultSet = preparedStatement.executeQuery();

            //this one get confession_id of
            //String getReplyIdMain = "SELECT confession_id FROM storeConfession_table WHERE reply_id =: deleteConfessionId";

            PreparedStatement ps1 = connection.prepareStatement(selectQuery);
//            PreparedStatement ps2 = connection.prepareStatement(getReplyIdMain);
//            ps2.executeUpdate(); //get rows

            while(resultSet.next()){
                String deleteConfess = deleteConfessionId;
//                ps1.setString(1, deleteConfessionId);
//                ps1.setString(2, deleteConfessionId);
                String c_id = resultSet.getString("confession_id");
                String r_id = resultSet.getString("reply_id");
//                File mainFile = new File("InputFiles/" + deleteConfess + ".txt");
//                System.out.println("DeleteConfess " + deleteConfess);
////                mainFile.delete();


//                if(r_id.equals("")){
//                    continue;
//                }
                deleteConfess = resultSet.getString("confession_id");
                System.out.println(deleteConfess);
                if(c_id.equals(deleteConfess)){
                    System.out.println("inside");
//                    ps1.setString(1, c_id);
//                    ps1.setString(2, r_id);
//                    mainFile.delete();
                }


//                ps1.executeUpdate();
            }
//            ResultSet resultSet =ps.executeQuery();
//            String rplyID = resultSet.getString("reply_id");

            //delete file
            //do for loop for more than 1 file
            System.out.println("Deleting the file");
            //get current file name and delete them



            System.out.println("----------------------------------------");
            System.out.println("File have been removed successfully");
            System.out.println("----------------------------------------");

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                statement.close();
                scanner.close();
                connection.close();
                System.out.println("Row deleted.");
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }
}
