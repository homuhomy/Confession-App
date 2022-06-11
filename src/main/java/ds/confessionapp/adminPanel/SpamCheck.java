package ds.confessionapp.adminPanel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class SpamCheck {

    //using Consine Similarity
    private static class Values {
        private int val1, val2;

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

        //make a queue so that the list will have the name of the text files from a directory
        Queue<String> tempFilesQueue = new Queue<>();
        File directory = new File("tempFiles");
        for(File f:directory.listFiles()){
            tempFilesQueue.enqueue(f.getName());
        }
        System.out.println(tempFilesQueue);

        while(tempFilesQueue.getSize() != 0){
            Path newConfessionPostPath = Path.of("tempFiles/" + tempFilesQueue.peek());
            System.out.println("----------------------------------------");
            System.out.println("Now checking " + tempFilesQueue.peek());
            System.out.println("----------------------------------------");
            String newConfessionPost = Files.readAllLines(newConfessionPostPath).stream().collect(Collectors.joining(" "));

            String comparedFile = "";

            //loop this
            double score = 0;

            File directoryPath = new File("InputFiles");
            //List of all files and directories
            String contents[] = directoryPath.list();
            //make it so that once it have been deleted it will get out of for loop
            for (int i = 0; i < contents.length; i++) {
                System.out.println(contents[i]);

                comparedFile = Files.readAllLines(Paths.get("InputFiles/" + contents[i])).stream().collect(Collectors.joining(" "));

                score = cs.score(newConfessionPost, comparedFile);
                System.out.println("Cosine similarity score = " + score);

                if (score > 0.90) {
                    //delete file
                    System.out.println("Deleting the file");
                    File file = new File("tempFiles/newPost.txt");
                    file.delete();

                    System.out.println("----------------------------------------");
                    System.out.println("File have been removed successfully");
                    System.out.println("----------------------------------------");

                    break;

                } else if(i == contents.length - 1){
                    System.out.println("File will be moved");

                    //loop through the files in InputFiles and get the LATEST file name
                    //substring the latest file / or get index of the latestFinalName
                    // eg: #UM0004.txt
                    String latestFileName = getLatestFileName().substring(3, 7);
                    int number = Integer.parseInt(latestFileName) + 1; //increase number for the new file

            /*  % denotes that it's a formatting instruction
                    0 is a flag that says pad with zero
                    4 denotes the length of formatted String,
                    this will ensure that the right number of zero should be added
                    d is for decimal which means the next argument should be an
                    integral value e.g. byte, char, short, int, or long. */

                    String str = String.format("%04d", number);  // 000x //x is nummber
                    String newPostNewName = "#UM" + str + ".txt";

                    String newPath = "InputFiles/" + newPostNewName;

                    Path temp = Files.move(newConfessionPostPath, Paths.get(newPath));

                    System.out.println("------------------------------------------------");
                    System.out.println("File have been moved and renamed successfully");
                    System.out.println("------------------------------------------------");

                    break;
                }
                else{
                    continue;
                }
            }
            tempFilesQueue.dequeue();
        }
        System.out.println("All files in tempFiles have been filtered.");
    }

    public static String getLatestFileName() {
        File directory = new File("InputFiles");
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
                    int s = name.indexOf('M') + 1;
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
}