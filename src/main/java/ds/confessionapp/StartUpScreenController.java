package ds.confessionapp;

import ds.confessionapp.adminPanel.DatabaseSaveData;
import ds.confessionapp.adminPanel.Queue;
import ds.confessionapp.adminPanel.SpamCheck;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;


import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class StartUpScreenController implements Initializable {

    //Confession c = new Confession();
    SpamCheck s = new SpamCheck();
    @FXML
    public Button ok, submitButton, viewButton, backForsubmitpage, backforviewpage, login, admin, backForadmin, backforAdminPanel, viewconfessionsbutton, submit, search,
            homeButtonIcon;
    @FXML
    public TextField input, pswdinput, confessID, searchField;
    @FXML
    public TextArea confession, displayTime;
    @FXML
    public Label XsuccessLabel, confessions, SubmissionTime, warningSubmit;

    @FXML
    Label st = new Label("submitTime");
    static Queue<String> confess = new Queue<>();
    static Queue<String> ID = new Queue<>();

    public static void QueueList(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://database-student-confession-aws.canrsxzrd6mg.us-west-1.rds.amazonaws.com:3306/UMCP_Database2", "root", "ds2022letsgo");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT confession_id,file_content FROM storeConfession_table ");
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                ID.enqueue(resultSet.getString("confession_id"));
                confess.enqueue(resultSet.getString("file_content"));

            }
//            System.out.println(confess.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    DatabaseSaveData d = new DatabaseSaveData();
    public static void main(String[] args) {
//        Timer timer = new Timer();
//        TimerTask task = new Helper();
//
//        timer.schedule(task, 200,5000);


    }

    public void switchScenes(ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;
//        System.out.println(event.getSource());

        if(event.getSource()== submitButton){
            //confession.setWrapText(true);
            stage = (Stage) submitButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("submitConfession.fxml"));
        }
        else if(event.getSource().equals(KeyCode.ENTER)){
            //stage = (Stage) submitButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("searchPage.fxml"));
        }
        else if(event.getSource()==search){
            stage = (Stage) search.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("searchPage.fxml"));
        }
        else if(event.getSource()==viewButton){
            stage = (Stage) viewButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("ViewConfessionPage.fxml"));

        }
        else if(event.getSource()==backForsubmitpage){
            stage = (Stage) backForsubmitpage.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("StartUpScreen.fxml"));
        }

        else if(event.getSource()==backforviewpage){
            stage = (Stage) backforviewpage.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("StartUpScreen.fxml"));
        }

        else if(event.getSource()==backForadmin){
            stage = (Stage) backForadmin.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("StartUpScreen.fxml"));
        }

        else if(event.getSource()==admin){
            stage = (Stage) admin.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
        }

        else if(event.getSource()==login){
            stage = (Stage) login.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
        }

        else if(event.getSource()==ok){
            stage = (Stage) ok.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
//            confessions.setText(confess.toString());
        }

        else if(event.getSource()==backforAdminPanel){
            stage = (Stage) backforAdminPanel.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("StartUpScreen.fxml"));
        }

        else if(event.getSource()==viewconfessionsbutton){
            viewconfessionsbutton.setVisible(false);
            confessions.setVisible(true);
            confessions.setText(confess.toString()); //when button is clicked, the confessions can be viewed
        }

        //KIV!!!! NEED TO CHANGE THE ENQUEUING PART
        else if(event.getSource()==submit){

            //submit new pst to tempFiles folder
            String data=confession.getText().trim(); //read contents of text area into 'data'
                String replyId = confessID.getText();
                String content;
                if(confessID.getText().isEmpty()){
                    content = confession.getText();
                }else{
                    content = "Replying to " + replyId + "\n\n" + confession.getText();
                }

                File f= new File("tempFiles");
                File[] listOfFiles = f.listFiles();

                int number = 1;
                if(listOfFiles.length > 0){ //if there's already existing files in tempFiles
                    String newName = getLatestFileNameTF().substring(7,8);
                    number = Integer.parseInt(newName) + 1;
                }
                String newPostName = "tempFiles/newPost" + number + ".txt";
                BufferedWriter toNewTxtFile = new BufferedWriter(new FileWriter(newPostName));
                try {
                    toNewTxtFile.write(content);

                    //either this one
//                LocalDateTime now = LocalDateTime.now();
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy hh:mm");
//                SubmissionTime.setText(formatter.format(now));

                    //or this
                Path file = Paths.get(newPostName);
                BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

                String s = DateTimeFormatter.ofPattern("uuuu-MMM-dd HH:mm:ss", Locale.ENGLISH)
                        .withZone(ZoneId.systemDefault())
                        .format(Instant.now());
                System.out.println("Creation Time: " + s); // yyyy-mm-dd 11:22:32
                st.setText(s);
                st.setVisible(true);

                }
                catch (RuntimeException | IOException e)
                {e.printStackTrace();}
                finally
                {
                    toNewTxtFile.close();
                }
                stage = (Stage) submit.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("submittedPage.fxml"));

        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void Success(){
        XsuccessLabel.setVisible(true);
        XsuccessLabel.setText("Login Successful!\nPress 'OK' to continue ");
        ok.setVisible(true);
    }
    @FXML
    public void Xsuccess(){
        XsuccessLabel.setVisible(true);
        XsuccessLabel.setText("Login Unsuccessful!\nPlease try again");
        ok.setVisible(false);
    }
    @FXML
    public void loginAction(ActionEvent event){
        int ans = verify(input.getText(),pswdinput.getText());
        if(ans==1){
            Success();
        }
        else{
           Xsuccess();
        }
//            System.exit(0);
    }

    public int verify(String username, String pswd){
        int found = 0; //false

        try{
            //Path dir = Paths.get("admin/AdminCredentials");
            Scanner read = new Scanner(new FileInputStream("admin/AdminCredentials"));
            while(read.hasNextLine()){
                if(username.equals(read.nextLine())){
                    if(pswd.equals(read.nextLine())){
                        found = 1;  //true
                    }
                }
            }
            read.close();
            System.out.println(found);
        }catch(Exception e){
            System.out.println("file not found - at verify method");
        }
        return found;
    }

    public void PostConfessions(ActionEvent event){
        confessions.setVisible(true);
        confessions.setText(confess.peek());
    }

    public void initialize() {
        ImageView imageView = new ImageView(getClass().getResource("/Users/homuhomy/IdeaProjects/Confession-App/src/main/resources/ds/confessionapp/images/addSubmission.png").toExternalForm());
        homeButtonIcon.setGraphic(imageView);
    }

    public static String getLatestFileNameTF() {
        File directory = new File("tempFiles");
        File[] files = directory.listFiles(File::isFile);
        File chosenFile = null;
        Arrays.sort(files, new Comparator<>() {
            @Override
            public int compare(File o1, File o2) {
                int n1 = extractNumber(o1.getName());
                int n2 = extractNumber(o2.getName());
                return n1 - n2;
            }

            private int extractNumber(String name) {
                int i = 0;
                try {
                    int s = name.indexOf('t') + 1;
                    int e = name.lastIndexOf('.');
                    String number = name.substring(s, e);
                    i = Integer.parseInt(number);
                } catch (Exception e) {
                    i = 0; // if filename does not match the format
                    // then default to 0
                }
                return i;
            }
        });

        for(File f : files) {
            chosenFile = f;
        }
        return chosenFile.getName();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

//    @FXML
//    private  void TimeNow(){
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy hh:mm");
//        TextArea textArea = new TextArea(formatter.format(now));
//    }

//    public void imageHome(){
//
//        Defaultview.setPickOnBounds(true); // allows click on transparent areas
//        Defaultview.setOnMouseClicked((MouseEvent e) -> {
//            FileChooser fileChooser = new FileChooser();
//            fileChooser.setTitle("Open Resource File");
//            fileChooser.showOpenDialog(new Stage());
//        });
//    }

}
