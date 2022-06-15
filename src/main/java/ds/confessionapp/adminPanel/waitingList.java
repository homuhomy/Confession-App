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

public class waitingList {
    static Queue<String> confess = new Queue<>();
    static Queue<String> ID = new Queue<>();

    public static void QueueList() {

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

    public static void WL(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println(confess.dequeue());
            }
        };

        TimerTask task1 = new TimerTask(){
         public void run() {
                System.out.println("1" + confess.dequeue());
            }
        };

        TimerTask task2 = new TimerTask(){
            public void run() {
                System.out.println("2"+confess.dequeue());
            }
        };


        while(!confess.isEmpty()&&confess.getSize()!=0) {
            int i=0;
            System.out.println( i +" "+ confess.getSize());

            if (confess.getSize() <= 5) {
                while (!confess.isEmpty()) {
                    if(confess.getSize()==0){break;}
                    timer.scheduleAtFixedRate(task, 10, 100 * 60);
                }
            }

            if (confess.getSize() <= 10) {
                while (!confess.isEmpty()) {
                    if(confess.getSize()<5) {break;}
                    timer.scheduleAtFixedRate(task1, 10, 100 * 40);

                }
            }

            if (confess.getSize()>10){
                while(!confess.isEmpty()){
                    if(confess.getSize()<10) {break;}
                    timer.scheduleAtFixedRate(task2, 10, 100 * 20);
                    }
            }

//            confess.clear();
            System.out.println("i "+i);
            i++;
            break;
        }

    }

//    public static void WaitingList() {
//        try {
//            Connection connection = DriverManager.getConnection("jdbc:mysql://34.124.213.155:3306/UMConfession_database", "root", "ds2022letsgo");
//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(file_content) FROM storeConfession_table ");
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//
//            QueueList();
//
//
//            while (!resultSet.next()) {
//
//                if (resultSet.getInt("count(file_content)") <= 5) {
//                    Timer timer = new Timer();
//                    TimerTask task = new TimerTask() {
//                        @Override
//                        public void run() {
//                            System.out.println(confess.dequeue());
//                        }
//                    };
//                    while (!confess.isEmpty()) {
//                        timer.scheduleAtFixedRate(task, 10, 10);
//                    }
//                } else if (resultSet.getInt("count(file_content)") <= 10) {
//                    Timer timer = new Timer();
//                    TimerTask task = new TimerTask() {
//                        @Override
//                        public void run() {
//                            System.out.println(confess.dequeue());
//                        }
//                    };
//                    timer.scheduleAtFixedRate(task, 20, 20);
//
//                } else {
//                    Timer timer = new Timer();
//                    TimerTask task = new TimerTask() {
//                        @Override
//                        public void run() {
//                            confess.dequeue();
//                        }
//                    };
//                    timer.schedule(task, 0, 15);
//                }
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//

    public static String batchRemoval(int i) {

        return confess.remove(i);
    }


    public static void main(String[] args) {
        QueueList();
        WL();
        }
}
