package ds.confessionapp;

import ds.confessionapp.adminPanel.DatabaseLoadData;

public class Confession {
    private String confession_id;
    private String file_content;
    private String reply_id;
    private String creation_date;

    // create a new confession object with all parameters except reply ID
    public Confession(String confession_id, String file_content, String creation_date) {
        this.confession_id = confession_id;
        this.file_content = file_content;
        this.creation_date = creation_date;
    }

    // Submit a confession and store in tempFolder  --> ???


    // create a new confession object with all parameters
    public Confession(String confession_id, String file_content, String reply_id, String creation_date) {
        this.confession_id = confession_id;
        this.file_content = file_content;
        this.reply_id = reply_id;
        this.creation_date = creation_date;
    }

    // Getters and setters
    public String getConfessionID() {
        return confession_id;
    }


    public String getContent() {
        return file_content;
    }

    public String getReplyID() {
        return reply_id;
    }

    public String getCreationDate() {
        return creation_date;
    }

    public void setContent(String file_content) {
        this.file_content = file_content;
    }


    public void setConfessionID(String confession_id) {
        this.confession_id = confession_id;
    }

    public void setReply_id(String reply_id) {
        this.reply_id = reply_id;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    @Override
    public String toString() {
        String details = "Post :" + confession_id;
        details = details + "\nDate : " + creation_date;
        details = details + "\n\nReply to : " + reply_id;
        details = details + "\n" + file_content;

        return details;
    }

    // inserting InputFiles into database



    /*
    // view published confessions from database
    // return all published confessions from database
    public static Confession viewPublishedConfession() {



  //  }


     */

    /*
    public static Confession getNextConfession() {

    }

    public static Confession getPreviousConfession() {
    }

    public static getConfessionReplies(String confession_id) {
if (!reply_id.equals)
    }

     */

    // for searching purposes

    /*
// method to get confessions by keywords
    public static Confession getConfessionByKeywords(String file_content) {
    String query = String.format("SELECT * FROM storeConfession_table " +
                        "WHERE file_content CONTAINS '%%%s%%'  ",
                file_content);
        return DatabaseLoadData.getDBObjects(query, storeConfession_table.class, -1);    // there's something wrong here at the return statement

    }

// method to get confessions by Confession ID
    public static Confession getConfessionByConfessionID(String confession_id) {
    String query = String.format("SELECT * FROM storeConfession_table " +
                        "WHERE confession_id CONTAINS '%%%s%%'  ",
                confession_id);
// return

    }

// method to get confessions by Creation Date
    public static Confession getConfessionByDate(String creation_date) {
    String query = String.format("SELECT * FROM storeConfession_table " +
                        "WHERE creation_date CONTAINS '%%%s%%'  ",
                creation_date);
// return
    }
*/



    public static void main(String args[]) {
        System.out.println("Hello");
    }
}



