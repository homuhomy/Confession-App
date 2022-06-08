package ds.confessionapp;

public class ConfessionSearchModel {
    String confession_id, file_content, reply_id,  creation_date;

    public ConfessionSearchModel(String confession_id, String file_content, String reply_id, String creation_date) {
        this.confession_id = confession_id;
        this.file_content = file_content;
        this.reply_id = reply_id;
        this.creation_date = creation_date;
    }

    public String getConfession_id() {
        return confession_id;
    }

    public void setConfession_id(String confession_id) {
        this.confession_id = confession_id;
    }

    public String getFile_content() {
        return file_content;
    }

    public void setFile_content(String file_content) {
        this.file_content = file_content;
    }

    public String getReply_id() {
        return reply_id;
    }

    public void setReply_id(String reply_id) {
        this.reply_id = reply_id;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }
}
