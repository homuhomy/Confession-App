package ds.confessionapp.adminPanel;

import org.w3c.dom.ls.LSOutput;

import java.sql.*;
import java.util.Scanner;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class waitingList {
    static Queue<String> confess = new Queue<>();
    public static void QueueList(){

        //queues everything in the Queue
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://34.124.213.155:3306/UMConfession_database", "root", "ds2022letsgo");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT confession_id,file_content FROM storeConfession_table ");
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                confess.enqueue(resultSet.getString("confession_id"));
                confess.enqueue(resultSet.getString("file_content"));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public static void WaitingList() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://34.124.213.155:3306/UMConfession_database", "root", "ds2022letsgo");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(file_content) FROM storeConfession_table ");
            ResultSet resultSet = preparedStatement.executeQuery();

            QueueList();
            Runnable task1 = () -> confess.dequeue(); //pop data meaning show in public post

            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

            while(resultSet.next()){
                System.out.println(resultSet.getInt("count(file_content)"));
                if(resultSet.getInt("count(file_content)")<=5){
                    executorService.schedule(task1, 1, TimeUnit.SECONDS);
//                    executorService.shutdown();
                }
                else if(resultSet.getInt("count(file_content)")<=10){
                    executorService.schedule(task1, 10, TimeUnit.SECONDS);
                    executorService.shutdown();
                }
                else {
                    executorService.schedule(task1, 5, TimeUnit.SECONDS);
                    executorService.shutdown();
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static String batchRemoval(int i){

        return confess.remove(i);
    }

    public static void main(String[] args) {
        WaitingList();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter an int: ");
        int num = sc.nextInt();
        batchRemoval(num);
        System.out.println(confess.toString());
    }

}