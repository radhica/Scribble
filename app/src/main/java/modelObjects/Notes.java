package modelObjects;

import java.io.Serializable;

/**
 * Created by rsampath on 7/24/15.
 */
public class Notes implements Serializable {
    private int id;
   private String title;
   private String description;
   private String lastModified;

    public Notes(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Notes(String title, String description, String dateString) {
        this.title = title;
        this.description = description;
        this.lastModified = dateString;
    }

    public Notes(int id, String title, String description, String dateString) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.lastModified = dateString;
    }

    public Notes() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
