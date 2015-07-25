package noteapplication.scribble.com.scribble;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * Created by rsampath on 7/24/15.
 */
public class NoteDetailActivity extends Activity {

    private EditText title;
    private EditText description;
    private Notes newNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_detail_activity);

        title = (EditText) findViewById(R.id.notes_detail_title);
        description = (EditText) findViewById(R.id.notes_details_description);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                Intent backIntent = new Intent();
                newNote = new Notes(title.getText().toString(),description.getText().toString());
                backIntent.putExtra("NEW_NOTE",newNote);
                setResult(RESULT_OK,  backIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}