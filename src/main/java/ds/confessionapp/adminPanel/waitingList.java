package ds.confessionapp.adminPanel;

import org.w3c.dom.ls.LSOutput;

import java.sql.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;
import java.util.Date;
import java.time.ZonedDateTime;

public class waitingList {
    static Queue<String> confess = new Queue<>();
    static Queue<String> ID = new Queue<>();

    public static void QueueList() {

        //queues everything in the Queue
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://database-student-confession-aws.canrsxzrd6mg.us-west-1.rds.amazonaws.com:3306/UMCP_Database2", "root", "ds2022letsgo");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT confession_id,file_content FROM storeConfession_table ");
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                ID.enqueue(resultSet.getString("confession_id"));
                confess.enqueue(resultSet.getString("file_content"));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void Wait(){
        try{
        Connection connection = DriverManager.getConnection("jdbc:mysql://database-student-confession-aws.canrsxzrd6mg.us-west-1.rds.amazonaws.com:3306/UMCP_Database2", "root", "ds2022letsgo");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT creation_date FROM storeConfession_table ");
        ResultSet resultSet = preparedStatement.executeQuery();


            while(!confess.isEmpty()) {
                int i=1;
                Date date = new Date();
                Date curr = new Date();
//                System.out.println("Date: " +date.getTime());
//                System.out.println(curr.getTime());

                if(confess.getSize()<=5){
                    //change the numbers to minutes
                    while(!(curr.getTime()-date.getTime()> 1000*10&&curr.getTime()-date.getTime()<= 20*1000)){
                        curr = new Date();

                    }
                    System.out.println(curr);
                    System.out.println("1 "+confess.dequeue());
                }

                else if(confess.getSize()<=10){
                    //change the numbers to minutes
                    while(!(curr.getTime()-date.getTime()> 1000*10&&curr.getTime()-date.getTime()<= 20*1000)){
                    curr = new Date();

                }
                System.out.println(curr);
                System.out.println("2 "+confess.dequeue());
                }

                else{
                    while(!(curr.getTime()-date.getTime()> 1000*10&&curr.getTime()-date.getTime()<= 20*1000)){
                        curr = new Date();

                    }
                    System.out.println(curr);
                    System.out.println("3 "+confess.dequeue());
                }

                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static void main(String[] args) {
        QueueList();
        Wait();

        }
}
