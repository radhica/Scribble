package noteapplication.scribble.com.scribble;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;

import adapters.NotesAdapter;
import modelObjects.Notes;


public class MainActivity extends ActionBarActivity {
    private static final int NOTE_REQUEST_CODE = 1000;
    private static final int ART_REQUEST_CODE = 1001;
    private FloatingActionButton addNote;
    private FloatingActionButton addArt;
    private FloatingActionsMenu menu;
    private ListView listViewNotes;
    private NotesAdapter notesAdapter;

    private ArrayList<Notes> listOfNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewNotes = (ListView) findViewById(R.id.list_view_notes);
        listOfNotes = new ArrayList<Notes>();
        listOfNotes.add(new Notes("Note 1", "Test 1"));
        listOfNotes.add(new Notes("Note 2", "Test 2"));
        notesAdapter = new NotesAdapter(MainActivity.this, 0, listOfNotes);
        listViewNotes.setAdapter(notesAdapter);

        listViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleOnItemOnSettings(position);
            }
        });
        menu = (FloatingActionsMenu) findViewById(R.id.add_actions);
        addArt = (FloatingActionButton) findViewById(R.id.action_add_art);
        addArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainActivity.this, DrawingActivity.class);
                startActivityForResult(newIntent, ART_REQUEST_CODE);
            }
        });

        addNote = (FloatingActionButton) findViewById(R.id.action_add_note);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(newIntent, NOTE_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            menu.collapse();
            switch (requestCode) {
                case NOTE_REQUEST_CODE:
                    if (data != null ) {
                        Object object =  data.getSerializableExtra("NEW_NOTE");
                        if(object !=null){
                            listOfNotes.add((Notes)object);
                            notesAdapter.notifyDataSetChanged();
                        }
                    }
                    break;
            }
        }
    }

    private void handleOnItemOnSettings(int position) {
        Toast.makeText(this, notesAdapter.getItem(position).getTitle(), Toast.LENGTH_LONG).show();
    }
}
