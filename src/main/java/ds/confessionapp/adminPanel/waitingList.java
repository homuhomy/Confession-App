package ds.confessionapp.adminPanel;

import ds.confessionapp.adminPanel.Queue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class waitingList {

    public static void main(String[] args) {
        String data="";
        Queue<String> confess = new Queue<>();
        String full ;
        int i=0;
        try {
            File myObj = new File("Test.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {

                String temp = myReader.nextLine()+"\n";
                data += temp; i++;
                if(temp.equals("\n")){
                    confess.enqueue(data);
                    System.out.println("Next confession");
                    data="";
                }

                System.out.println("i = " + i);
//                System.out.println(data);

            }
//            System.out.println(data);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        confess.enqueue(data);
//        String con = "Hello world";
//
//        String [] elements =  con.split(" ");
//        int j;
//        for(j =0; j<elements.length; j++)
//            System.out.println(elements[j]);

//            confess.enqueue(data);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        Runnable task1 = () -> System.out.println(confess.toString());

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