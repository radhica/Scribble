package noteapplication.scribble.com.scribble;

import java.io.Serializable;

/**
 * Created by rsampath on 7/24/15.
 */
public class Notes implements Serializable {
    String title;
    String description;

    public Notes(String title, String description) {
        this.title = title;
        this.description = description;
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

}
