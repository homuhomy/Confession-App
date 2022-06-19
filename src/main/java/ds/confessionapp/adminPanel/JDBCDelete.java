package ds.confessionapp.adminPanel;

import ds.confessionapp.ConfessionSearchModel;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.sql.*;
import java.util.Scanner;

public class JDBCDelete {

//    public static void main(String[] args) {
//        //declare resources
//        Statement statement = null;
//        Scanner scanner = null;
//
//        DatabaseConnection connectNow = new DatabaseConnection();
//        Connection connection = DatabaseConnection.getConnection();
//        //unlink query
//
//        try{
//            //prepare statement
//            statement = DatabaseConnection.getConnection().createStatement();
//            scanner = new Scanner(System.in);
//
//            System.out.println("Enter the confession id that will be deleted: ");
//            String deleteConfessionId = scanner.nextLine();
//            //String deleteFromMainId = "DELETE FROM storeConfession_table WHERE confession_id = ?";
//            String selectQuery = "DELETE FROM storeConfession_table WHERE confession_id = ? OR reply_id = ?";
//
//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT confession_id,file_content,reply_id,creation_date FROM storeConfession_table ");
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            //this one get confession_id of
//            //String getReplyIdMain = "SELECT confession_id FROM storeConfession_table WHERE reply_id =: deleteConfessionId";
//
//            PreparedStatement ps1 = connection.prepareStatement(selectQuery);
////            PreparedStatement ps2 = connection.prepareStatement(getReplyIdMain);
////            ps2.executeUpdate(); //get rows
//
//            while(resultSet.next()) {
//                String deleteConfess = deleteConfessionId;
//                ps1.setString(1, deleteConfess);
//
//                String c_id = resultSet.getString("confession_id");
//                String r_id = resultSet.getString("reply_id");
//                deleteConfess = c_id;
//                ps1.setString(2, deleteConfess);
//                if(r_id.equals("")){
//                    continue;
//                }
//
//                else if(r_id.equals(deleteConfess)){
//                    ps1.setString(1, c_id);
//                    ps1.setString(2, r_id);
//
//                }
//
//
//                ps1.executeUpdate();
//            }
////            ResultSet resultSet =ps.executeQuery();
////            String rplyID = resultSet.getString("reply_id");
//
//            //delete file
//            //do for loop for more than 1 file
//            System.out.println("Deleting the file");
//            //get current file name and delete them
//            File mainFile = new File("InputFiles/" + deleteConfessionId + ".txt");
//            System.out.println("input");
////            mainFile.delete();
//
//
//            System.out.println("----------------------------------------");
//            System.out.println("File have been removed successfully");
//            System.out.println("----------------------------------------");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            try{
//                statement.close();
//                scanner.close();
//                connection.close();
//                System.out.println("Row deleted.");
//            } catch (SQLException e2) {
//                e2.printStackTrace();
//            }
//        }
//    }

    public static void main(String[] args) {
        batchremoval();
    }
    public static boolean batchremoval(){
        Scanner z = new Scanner (System.in);
        System.out.print("Please enter id post you wanna delete: ");
        String post = z.next();

        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://database-student-confession-aws.canrsxzrd6mg.us-west-1.rds.amazonaws.com:3306/UMCP_Database2", "root", "ds2022letsgo");
            Statement st =  con.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM storeConfession_table WHERE confession_id LIKE'%"+post+"%'");

            if(resultSet.next()){
                ResultSet rs = st.executeQuery("SELECT * FROM storeConfession_table WHERE confession_id  LIKE'%"+post+"%'");
               String check ;
                while (rs.next()){
                    check = rs.getString("confession_id");
                    remove(check);
                }
            }
            else{
                System.out.println("The post you wanna find and delete is not available."); //prompt if not available, post.setText("This room is unavailable at the selected time!");
            }

            resultSet.close();
            con.close();

        }
//        catch (ClassNotFoundException e) {
//            System.out.println("Error 1");
//            System.out.println(e);
//        }
        catch (SQLException e){
            System.out.println("Error 2");
            System.out.println(e);
        }
        return true;
    }

    public static String remove (String x){
        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://database-student-confession-aws.canrsxzrd6mg.us-west-1.rds.amazonaws.com:3306/UMCP_Database2", "root", "ds2022letsgo");
            Statement st =  con.createStatement();
            ResultSet resultSet = st.executeQuery(" SELECT * FROM storeConfession_table WHERE reply_id LIKE '%"+x+"%'");

            if(resultSet.next()){
                ResultSet rs = st.executeQuery(" SELECT * FROM storeConfession_table WHERE reply_id LIKE '%"+x+"%'");
                String check;
                while (rs.next()){
                    check = rs.getString("confession_id");
                    if(remove(check)=="no"){
                        remove(x);
                    }
                    else{
                        remove(check);
                    }
                }
            }
            else{
                st.executeUpdate(" DELETE FROM storeConfession_table WHERE confession_id ");
                return "no";
            }

            resultSet.close();
            con.close();

        }
//        catch (ClassNotFoundException e) {
//            System.out.println("Error 1");
//            System.out.println(e);
//        }
        catch (SQLException e){
            System.out.println("Error ");
            System.out.println(e);
        }
        return "no";
    }
}

