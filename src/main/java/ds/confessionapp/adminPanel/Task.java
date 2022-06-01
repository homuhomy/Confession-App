package ds.confessionapp.adminPanel;

import java.util.Timer;
import java.util.TimerTask;

public class Task extends TimerTask {


    public static void main(String[] axrgs) {
        Timer timer = new Timer();
        TimerTask task = new Task();
        timer.schedule(task, 10000, 5000);
//        timer.scheduleAtFixedRate(task, 2000,5000);
    }

    public static void waitingList(){
//        if()
    }

    @Override
    public void run() {
        System.out.println("works");
        System.out.println("works1");
        System.out.println("works2");
    }
}
