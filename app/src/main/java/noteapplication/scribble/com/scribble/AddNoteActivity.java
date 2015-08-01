package noteapplication.scribble.com.scribble;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;

import db.DatabaseHandler;
import modelObjects.Notes;

/**
 * Created by rsampath on 7/24/15.
 */
public class AddNoteActivity extends ActionBarActivity {

    private EditText title;
    private EditText description;
    private Notes newNote;
    private Intent intent;
    private DatabaseHandler db;
    private boolean editNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_activity);

        db = new DatabaseHandler(this);

        title = (EditText) findViewById(R.id.notes_detail_title);
        description = (EditText) findViewById(R.id.notes_details_description);

        intent = getIntent();
        if (intent.getExtras() != null) {
            if (intent.getExtras().containsKey("EDIT_NOTE")) {
                newNote = (Notes) intent.getSerializableExtra("NOTES_OBJECT");
                title.setText(newNote.getTitle());
                description.setText(newNote.getDescription());
                editNote = true;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent backIntent = new Intent();
        switch (item.getItemId()) {
            case R.id.action_save:
                if (editNote) {
                    newNote.setDescription(description.getText().toString());
                    newNote.setTitle(title.getText().toString());
                    newNote.setLastModified(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis())));
                    db.updateNote(newNote);
                } else {
                    newNote = new Notes(title.getText().toString(),
                            description.getText().toString());
                    db.addNote(newNote);
                }
                backIntent.putExtra("NOTE_CHANGED", true);
                setResult(RESULT_OK, backIntent);
                finish();
                return true;
            case R.id.action_discard:
                backIntent.putExtra("NOTE_CHANGED", false);
                setResult(RESULT_OK, backIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent();
        setResult(RESULT_OK, backIntent);
        finish();
    }
}