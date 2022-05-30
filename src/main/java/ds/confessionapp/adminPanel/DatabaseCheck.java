package ds.confessionapp.adminPanel;

import java.sql.*;

public class DatabaseCheck {

    //category we need
    //private String confessionText (actual content of the confession) TEXT
    //private String? confessionID VARCHAR
    //private String? replyConfessionID VARCHAR (if the new post is replying to other confession, else NULL)

    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "ds2022letsgo";
    private static final String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://34.124.213.155:3306/UMConfession_database";


    private static Connection connection = null;
    static{
        try {
            Class.forName(DB_DRIVER_CLASS);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return connection;
    }


}


