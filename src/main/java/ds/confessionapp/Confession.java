package ds.confessionapp;

import ds.confessionapp.adminPanel.DatabaseCheck;
import ds.confessionapp.adminPanel.DatabaseLoadData;

import java.sql.*;

public class Confession {
    private String confession_id;
    private String file_content;
    private String reply_id;
    private String creation_date;

    public Confession() {
    }

    // create a new confession object with all parameters except reply ID
    public Confession(String confession_id, String file_content, String creation_date) {
        this.confession_id = confession_id;
        this.file_content = file_content;
        this.creation_date = creation_date;
    }

    // create a new confession object with all parameters
    public Confession(String confession_id, String file_content, String reply_id, String creation_date) {
        this.confession_id = confession_id;
        this.file_content = file_content;
        this.reply_id = reply_id;
        this.creation_date = creation_date;
    }

    // Getters and setters
    public String getConfessionID() {
        return confession_id;
    }

    public String getContent() {
        return file_content;
    }

    public String getReplyID() {
        return reply_id;
    }

    public String getCreationDate() {
        return creation_date;
    }

    public void setContent(String file_content) {
        this.file_content = file_content;
    }


    public void setConfessionID(String confession_id) {
        this.confession_id = confession_id;
    }

    public void setReply_id(String reply_id) {
        this.reply_id = reply_id;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    @Override
    public String toString() {
        String details = "Post :" + confession_id;
        details = details + "\nDate : " + creation_date;
        details = details + "\n\nReply to : " + reply_id;
        details = details + "\n" + file_content;

        return details;
    }

    // view published confessions from database
    // return all published confessions from database
    public static void viewConfession() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://34.124.213.155:3306/UMConfession_database", "root", "ds2022letsgo");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT storeConfession_table ORDER BY confession_id asc");
            ResultSet resultSet = preparedStatement.executeQuery();

            int position = 0;
            while (resultSet.next()) {
                resultSet.getString("confession_id");
                resultSet.getString("file_content");
                resultSet.getString("creation_date");
                resultSet.getString("reply_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // for searching purposes
    // method to get confessions by keywords
    public static void findByKeywords(String file_content) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://34.124.213.155:3306/UMConfession_database", "root", "ds2022letsgo");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT file_content FROM storeConfession_table WHERE file_content CONTAINS '%%%s%%'");
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                resultSet.getString("confession_id");
                resultSet.getString("file_content");
                resultSet.getString("creation_date");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // method to get confessions by Confession ID
    public static void findbyID(String confession_id) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://34.124.213.155:3306/UMConfession_database", "root", "ds2022letsgo");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT file_content FROM storeConfession_table WHERE confession_id = '%s'");
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                resultSet.getString("file_content");
                resultSet.getString("creation_date");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // method to get confessions by Creation Date
    public static void findByCreationDate(String creation_date) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://34.124.213.155:3306/UMConfession_database", "root", "ds2022letsgo");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT confession_id,file_content FROM storeConfession_table WHERE creation_date = '%%%s%%'");
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                resultSet.getString("confession_id");
                resultSet.getString("file_content");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    // anati's example
        // method to get confessions by Confession ID
         Connection connection = null;
         ResultSet rs = null;
         PreparedStatement ps = null;

        public static ResultSet findByID(String s) throws SQLException {
            connection = DatabaseCheck.getConnection();
            ps = connection.prepareStatement("SELECT * FROM storeConfession_table " +
                    "WHERE confession_id = ?");
            rs = ps.executeQuery();
            return rs;

        }
    */
    public static void main(String args[]) {
        System.out.println("Hello");
    }
}


