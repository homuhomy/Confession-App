//package ds.confessionapp.adminPanel;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.Scanner;
//
//public class SpamCheck {
//    //ok lets say there's a new confession submission, let store it temporarily as newPost.txt
//    //newPost.txt will be compared with all the existing text files in the database.
//    // if similarity >90%, either flagged or delete submission
//    //if similariy <90%, the post will now have its own confession_Id (maybe check database last confession_id?) and will proceed to be saved in the database as usual
//    //file will be in the queue
//    private static double similarity;
//
//    public class ReadFile {
//        public static String readFile(String filename) {
//            StringBuffer sb = new StringBuffer();
//            try {
//                File file = new File(filename);
//                Scanner input = new Scanner(file);
//                while (input.hasNextLine()) {
//                    sb.append(input.nextLine() + "\n");
//                }
//                input.close();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//            return sb.toString();
//        }
//
//    public static void main(String[] args) throws IOException {
//
//        String newCofPost = readFile("tempFiles/newPost.txt") ;
//        System.out.println(newCofPost);
//
//        double idf = Math.log(1/1);
//        System.out.println("informe o termo");
//        Scanner in = new Scanner(System.in);
//        String term = in.nextLine();
//        System.out.println("informe o documento(arquivo/nome do arquivo)");
//        String doc = in.nextLine();
//    }
//
////        File newConfessionPost = new File("tempFiles/newPost.txt");
////        InputStream inputStream = new FileInputStream(newConfessionPost);
////
////        Path dir = Paths.get("InputFiles");
////
////
////        if(similarity == 0){
////            Files.copy(inputStream, Paths.get("RetrievedFilesFromDB/"+ "getTheNewConfessionId"));
////        }else{
////            newConfessionPost.delete();
////        }
//    }
//
//        public class TFIDFCalculator {
//            public double tf(List<String> doc, String term) {
//                double result = 0;
//                for (String word : doc) {
//                    if (term.equalsIgnoreCase(word))
//                        result++;
//                }
//                return result / doc.size();
//            }
//
//            public double idf(List <List <String>> docs, String term) {
//                double n = 0;
//                for (List <String> doc : docs) {
//                    for (String word : doc) {
//                        if (term.equalsIgnoreCase(word)) {
//                            n++;
//                            break;
//                        }
//                    }
//                }
//                return Math.log(docs.size() / n);
//            }
//
//            public double tfidf(List <String> doc, List <List <String>> docs, String term) {
//                return tf(doc, term) * idf(docs, term);
//            }
//
//
//}
