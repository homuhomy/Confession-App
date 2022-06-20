package ds.confessionapp.adminPanel;

import ds.confessionapp.AdminPanelController;
import ds.confessionapp.ConfessionSearchModel;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCDelete {

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

    public static void main(String[] args) {
        JDBCDelete j = new JDBCDelete();
        Scanner sc = new Scanner(System.in);
        String id = sc.next();
        j.loop(id);

//            j.deletePost(id);

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

