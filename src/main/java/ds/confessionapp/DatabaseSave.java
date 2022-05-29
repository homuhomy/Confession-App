package ds.confessionapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class DatabaseSave {

    public static void main(String[] args) throws SQLException {

        //insert value to the table //? represent placeholder
        //add date creation for when the confession is made
        String SQL = "INSERT INTO storeConfession_table (confession_id,file_content,reply_id,creation_date)VALUES(?,?,?,?)";

        Path dir = Paths.get("InputFiles"); //creates a Path object called dir that points to where it will be getting its files from.

        //uses this path object to get a FileInputStream object which is used as an input stream for reading data from files on disk
        //This method returns null if there was no file found or if there was an error opening it
        try (Stream<Path> list = Files.list(dir);
             Connection connection = DatabaseCheck.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)) {
            List<Path> pathList = list.collect(Collectors.toList());
            System.out.println("Following files are saved in database:");
            for (Path path : pathList) {

                System.out.println(path.getFileName()); //get the file name based on txt file

                File file = path.toFile();
                String confession_id = file.getName(); //use the file name as confession_id

                //to find out if the text include "Replying to #UMXXXXXX"
                String reply_id;
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    // Get and process first line:
                    reply_id = "";
                    String line = reader.readLine(); //Get the first line. You could consider reader as a queue (sort-of), where readLine() dequeues the first element in the reader queue.
                    if (line.startsWith("Replying")) {
                        reply_id = line.substring(12);//Create String of that line and substring starting from #.
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //get date of the file
                BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);

                ps.setString(1, confession_id.substring(0,9));
                ps.setCharacterStream(2, new FileReader(file)); //get the file into column
                ps.setString(3, reply_id);  //later change

                String dateCreation = String.valueOf(attr.creationTime());
                ps.setString(4, dateCreation.substring(0,10));

                ps.addBatch();
            }
            System.out.println("----------------------------------------");
            int[] executeBatch = ps.executeBatch();
            for (int i : executeBatch) {
                System.out.println(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
