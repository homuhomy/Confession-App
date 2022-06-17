package ds.confessionapp;

import ds.confessionapp.adminPanel.Queue;

import java.sql.*;

public class ViewTable {

    String confession_id, file_content;
    Date creation_date;

    public ViewTable(String confession_id, String file_content, Date creation_date) {
        this.confession_id = confession_id;
        this.file_content = file_content;
        this.creation_date = creation_date;
    }
    public String getConfession_id() {
        return confession_id;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
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


}
