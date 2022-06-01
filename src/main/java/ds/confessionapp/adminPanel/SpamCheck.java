package ds.confessionapp.adminPanel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SpamCheck {

    //using Consine Similarity
    private static class Values {

        private int val1;
        private int val2;

        private Values(int v1, int v2) {
            this.val1 = v1;
            this.val2 = v2;
        }

        public void updateValues(int v1, int v2) {
            this.val1 = v1;
            this.val2 = v2;
        }
    }//end of class values

    public double score(String newConfessionPost, String comparedFile) {
        //Identify distinct words from both documents
        String[] newConfessionPostWords = newConfessionPost.split(" ");
        String[] comparedFileWords = comparedFile.split(" ");
        Map<String, Values> wordFreqVector = new HashMap<>();
        List<String> distinctWords = new ArrayList<>();

        //prepare word frequency vector by using newConfessionPost
        for (String text : newConfessionPostWords) {
            String word = text.trim();
            if (!word.isEmpty()) {
                if (wordFreqVector.containsKey(word)) {
                    Values vals1 = wordFreqVector.get(word);
                    int freq1 = vals1.val1 + 1;
                    int freq2 = vals1.val2;
                    vals1.updateValues(freq1, freq2);
                    wordFreqVector.put(word, vals1);
                } else {
                    Values vals1 = new Values(1, 0);
                    wordFreqVector.put(word, vals1);
                    distinctWords.add(word);
                }
            }
        }

        //prepare word frequency vector by using comparedFile
        for (String text : comparedFileWords) {
            String word = text.trim();
            if (!word.isEmpty()) {
                if (wordFreqVector.containsKey(word)) {
                    Values vals1 = wordFreqVector.get(word);
                    int freq1 = vals1.val1;
                    int freq2 = vals1.val2 + 1;
                    vals1.updateValues(freq1, freq2);
                    wordFreqVector.put(word, vals1);
                } else {
                    Values vals1 = new Values(0, 1);
                    wordFreqVector.put(word, vals1);
                    distinctWords.add(word);
                }
            }
        }

        //calculate the cosine similarity score.
        double vectAB = 0.0000000;
        double vectA = 0.0000000;
        double vectB = 0.0000000;

        for (int i = 0; i < distinctWords.size(); i++) {
            Values vals12 = wordFreqVector.get(distinctWords.get(i));
            double freq1 = vals12.val1;
            double freq2 = vals12.val2;
            //System.out.println(distinctWords.get(i) + "#" + freq1 + "#" + freq2);
            vectAB = vectAB + freq1 * freq2;
            vectA = vectA + freq1 * freq1;
            vectB = vectB + freq2 * freq2;
        }

        //System.out.println("VectAB " + vectAB + " VectA_Sq " + vectA + " VectB_Sq " + vectB);
        return ((vectAB) / (Math.sqrt(vectA) * Math.sqrt(vectB)));
    }

    public static void main(String[] args) throws IOException {
        SpamCheck cs = new SpamCheck();

        Path newConfessionPostPath = Path.of("tempFiles/newPost.txt");
        String newConfessionPost = Files.readAllLines(newConfessionPostPath).stream().collect(Collectors.joining(" "));
        String comparedFile = Files.readAllLines(Paths.get("InputFiles/#UM0002.txt")).stream().collect(Collectors.joining(" "));

        double score = cs.score(newConfessionPost, comparedFile);
        System.out.println("Cosine similarity score = " + score);

        //if similarity less than 0.9, move the file to InputFiles so it cant be queued to be post
        if (score < 0.90) {
            System.out.println("File will be moved");
            String newFileName = "";

            //loop through the files in InputFiles and get the LATEST file name
            //substring the latest file / or get index of the latestFinalName
            // eg: #UM0004.txt
            String latestFileName = getLatestFileName().substring(3,7);
            int number = Integer.parseInt(latestFileName) + 1; //increase the number



            /*  % denotes that it's a formatting instruction
                    0 is a flag that says pad with zero
                    4 denotes the length of formatted String,
                    this will ensure that the right number of zero should be added
                    d is for decimal which means the next argument should be an
                    integral value e.g. byte, char, short, int, or long. */

            String str = String.format("%04d", number);  // 0009
            String newPostNewName = "#UM" + str + ".txt";

            String newPath = "InputFiles/" + newPostNewName;

            Path temp = Files.move(newConfessionPostPath, Paths.get(newPath));

            if (temp != null) {
                System.out.println("-----------------------------------");
                System.out.println("File moved successfully");
                System.out.println("-----------------------------------");

            } else {
                System.out.println("Failed to move the file");
            }
        } else {
            //delete file / masuk bin
            System.out.println("Deleting the file");
        }

    }
    public static String getLatestFileName()
    {
        File directory = new File("InputFiles");
        File[] files = directory.listFiles(File::isFile);
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;

        if (files != null)
        {
            for (File file : files)
            {
                if (file.lastModified() > lastModifiedTime)
                {
                    chosenFile = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }

        return chosenFile.getName();
    }

}