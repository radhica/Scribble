package modelObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * Created by rsampath on 7/24/15.
 */
public class Arts implements Serializable {
    private int id;
   private String title;
   private Bitmap bmp;
   private String lastModified;

    public Arts(String title, Bitmap description) {
        this.title = title;
        this.bmp = description;
    }

    public Arts(String title, Bitmap description, String dateString) {
        this.title = title;
        this.bmp = description;
        this.lastModified = dateString;
    }

    public Arts(int id, String title, Bitmap description, String dateString) {
        this.id = id;
        this.title = title;
        this.bmp = description;
        this.lastModified = dateString;
    }

    public Arts() {

    }

    public Bitmap getDescription() {
        return bmp;
    }

    public void setDescription(Bitmap description) {
        this.bmp = description;
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

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getPhoto(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}
