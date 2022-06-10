package ds.confessionapp.adminPanel;

import org.w3c.dom.ls.LSOutput;

import java.sql.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class waitingList extends TimerTask{
    static Queue<String> confess = new Queue<>();
    static Queue<String> ID = new Queue<>();
    public static void QueueList(){

        //queues everything in the Queue
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://34.124.213.155:3306/UMConfession_database", "root", "ds2022letsgo");
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


    public static void WaitingList() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://34.124.213.155:3306/UMConfession_database", "root", "ds2022letsgo");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(file_content) FROM storeConfession_table ");
            ResultSet resultSet = preparedStatement.executeQuery();

            QueueList();

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    confess.dequeue();
                }
            };
            while(!resultSet.next()){

                if(resultSet.getInt("count(file_content)")<=5){
                    timer.scheduleAtFixedRate(task,10, 10);

                }
                else if(resultSet.getInt("count(file_content)")<=10){
                    timer.scheduleAtFixedRate(task,20, 20);

                }
                else {
                    timer.schedule(task,0,15);
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
//        WaitingList();
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter an int: ");
//        int num = sc.nextInt();
//        batchRemoval(num);
//        System.out.println(confess.toString());
//        Timer timer = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                confess.dequeue();
//            }
//        };
//
//        timer.schedule(task, 200,5000);

    }

    @Override
    public void run() {

    }
}