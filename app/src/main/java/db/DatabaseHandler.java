package db;

/**
 * Created by rsampath on 7/25/15.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import modelObjects.Arts;
import modelObjects.Notes;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "DATABASE_NOTES";

    private static final String TABLE_NOTES = "Notes";

    private static final String KEY_ID_NOTE = "note_id";
    private static final String KEY_TITLE_NOTE = "note_title";
    private static final String KEY_DESCRIPTION_NOTE = "note_description";
    private static final String KEY_DATE_MODIFIED_NOTE = "note_lastModified";

    private static final String TABLE_ARTS = "Arts";

    private static final String KEY_ID_ART = "note_id";
    private static final String KEY_TITLE_ART = "note_title";
    private static final String KEY_DESCRIPTION_ART = "note_photo_blob";
    private static final String KEY_DATE_MODIFIED_ART = "note_lastModified";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
                + KEY_ID_NOTE + " INTEGER PRIMARY KEY," + KEY_TITLE_NOTE + " TEXT,"
                + KEY_DESCRIPTION_NOTE + " TEXT," + KEY_DATE_MODIFIED_NOTE + "  DATETIME DEFAULT CURRENT_TIMESTAMP); ";

        String CREATE_ARTS_TABLE = "create table "
                + TABLE_ARTS + " (" + KEY_ID_ART
                + " integer primary key autoincrement, " + KEY_DESCRIPTION_ART
                + " blob not null, " + KEY_TITLE_ART + " text not null unique, "
                + KEY_DATE_MODIFIED_ART + "  DATETIME DEFAULT CURRENT_TIMESTAMP);";


        db.execSQL(CREATE_NOTES_TABLE);
        db.execSQL(CREATE_ARTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public void addNote(Notes note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE_NOTE, note.getTitle());
        values.put(KEY_DESCRIPTION_NOTE, note.getDescription());

        db.insert(TABLE_NOTES, null, values);
        db.close(); // Closing database connection
    }

    public void addArt(Arts art) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_DESCRIPTION_ART, Arts.getBytes(art.getDescription()));
        cv.put(KEY_TITLE_ART, art.getTitle());

        db.insert(TABLE_ARTS, null, cv);
    }

    public Notes getNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTES, new String[] {KEY_ID_NOTE,
                        KEY_TITLE_NOTE, KEY_DESCRIPTION_NOTE, KEY_DATE_MODIFIED_NOTE}, KEY_ID_NOTE + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Notes note = new Notes(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return note;
    }

    public Arts getArt(int id) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(true, TABLE_ARTS, new String[] { KEY_ID_ART,
                KEY_TITLE_ART, KEY_DESCRIPTION_ART, KEY_DATE_MODIFIED_ART }, KEY_ID_ART + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Arts art = new Arts(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Arts.getPhoto(cursor.getBlob(2)), cursor.getString(3));
        return art;

    }


    public List<Notes> getAllNotes() {
        List<Notes> notesList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NOTES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Notes note = new Notes();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setDescription(cursor.getString(2));
                note .setLastModified(cursor.getString(3));
                notesList.add(note);
            } while (cursor.moveToNext());
        }

        return notesList;
    }

    public int updateNote(Notes note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE_NOTE, note.getTitle());
        values.put(KEY_DESCRIPTION_NOTE, note.getDescription());
        values.put(KEY_DATE_MODIFIED_NOTE, note.getLastModified());

        return db.update(TABLE_NOTES, values, KEY_ID_NOTE + " = ?",
                new String[] { String.valueOf(note.getId()) });
    }

    public void deleteNote(Notes note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_ID_NOTE + " = ?",
                new String[] { String.valueOf(note.getId()) });
        db.close();
    }


    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NOTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
