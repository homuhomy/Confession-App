package ds.confessionapp.adminPanel;

import ds.confessionapp.adminPanel.Queue;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class waitingList {

    public static void main(String[] args) throws SQLException {
        String confession="";
        Queue<String> confess = new Queue<>();
        Queue<String> Replyconfess = new Queue<>();
        String full ;
        int i=0;
//        try {
//            File myObj = new File("C:\\Users\\User\\IdeaProjects\\Confession-App\\InputFiles\\#UM011411.txt");
//            Scanner myReader = new Scanner(myObj);
//            while (myReader.hasNextLine()) {
////                System.out.println(myReader.nextLine() + " " + i);
//
//                String replyConfess = myReader.nextLine();
////                System.out.println(replyConfess.substring(0,8));
////                System.out.println(replyConfess.substring(0));
//                if(replyConfess.substring(0,8).equalsIgnoreCase("replying")) {
//                    System.out.println(replyConfess.toString());
//                    Replyconfess.enqueue(replyConfess);
//                }
////                else{
////                confession += replyConfess;
////                if(replyConfess.equals("\n")){
////                    System.out.println("The confession");
////                    confess.enqueue(confession);
////                    System.out.println(confess.toString());
////                    confession="";
////                }
////                }
//                i++;
//            }
//
//            myReader.close();
//        } catch (FileNotFoundException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }

        try{
            String string = "#UM011411";
            Connection connection = DriverManager.getConnection("jdbc:mysql://34.124.213.155:3306/UMConfession_database", "root", "ds2022letsgo");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM storeConfession_table WHERE confession_id = '"+string+"'");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        confess.enqueue(confession);

//            confess.enqueue(confession);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        Runnable task1 = () -> System.out.println(confess);

        //if elements are 5 elements or less
        if(i<=5)
            executorService.schedule(task1, 5, TimeUnit.SECONDS);

        //if elements are 10 elements or less
        else if(i<=10)
            executorService.schedule(task1, 10, TimeUnit.SECONDS);

        //if more than 10 elements
        else
            executorService.schedule(task1, 15, TimeUnit.SECONDS);

        executorService.shutdown();
    }

}