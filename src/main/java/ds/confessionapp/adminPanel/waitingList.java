package ds.confessionapp.adminPanel;

import java.sql.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class waitingList {
    static Queue<String> confess = new Queue<>();
    public static void QueueList(){



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

//
//        Runnable task1 = () -> System.out.println(confess);
//
//        //if elements are 5 elements or less
//        if(i<=5)
//            executorService.schedule(task1, 5, TimeUnit.SECONDS);
//
//        //if elements are 10 elements or less
//        else if(i<=10)
//            executorService.schedule(task1, 10, TimeUnit.SECONDS);
//
//        //if more than 10 elements
//        else
//            executorService.schedule(task1, 15, TimeUnit.SECONDS);
//
//        executorService.shutdown();
    }


    public static void WaitingList() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://34.124.213.155:3306/UMConfession_database", "root", "ds2022letsgo");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(file_content) FROM storeConfession_table ");
            ResultSet resultSet = preparedStatement.executeQuery();

            QueueList();
            Runnable task1 = () -> System.out.println(confess.dequeue() + " "+confess.dequeue()); //pop data meaning show in public post

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

    public static void main(String[] args) {
        WaitingList();
    }

}