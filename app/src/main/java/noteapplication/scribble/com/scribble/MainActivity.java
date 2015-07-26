package noteapplication.scribble.com.scribble;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.app.SearchManager;
import android.content.Context;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;

import java.util.ArrayList;

import adapters.NotesAdapter;
import db.DatabaseHandler;
import modelObjects.Notes;


public class MainActivity extends ActionBarActivity {
    private static final int NOTE_REQUEST_CODE = 1000;
    private static final int ART_REQUEST_CODE = 1001;
    private FloatingActionButton addNote;
    private FloatingActionButton addArt;
    private FloatingActionsMenu menu;
    private DynamicListView listViewNotes;
    private NotesAdapter notesAdapter = null;

    private ArrayList<Notes> listOfNotes;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewNotes = (DynamicListView) findViewById(R.id.list_view_notes);
        db = new DatabaseHandler(this);

        listOfNotes = new ArrayList<>();
        listOfNotes = (ArrayList<Notes>) db.getAllNotes();
        notesAdapter = new NotesAdapter(MainActivity.this, 0, listOfNotes);
        listViewNotes.setAdapter(notesAdapter);
        listViewNotes.setTextFilterEnabled(true);

        listViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    handleOnItemOnSettings(position);
            }
        });

        listViewNotes.enableSwipeToDismiss(
                new OnDismissCallback() {
                    @Override
                    public void onDismiss(@NonNull final ViewGroup listView, @NonNull final int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            db.deleteNote(notesAdapter.getItem(position));
                            notesAdapter.remove(position);
                            notesAdapter.notifyDataSetChanged();
                        }
                    }
                }
        );


        menu = (FloatingActionsMenu) findViewById(R.id.add_actions);
        addArt = (FloatingActionButton) findViewById(R.id.action_add_art);
        addArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainActivity.this, DrawingActivity.class);
                newIntent.putExtra("EDIT_NOTE",false);
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
        Intent newIntent = new Intent(MainActivity.this, AddNoteActivity.class);
        newIntent.putExtra("NOTES_OBJECT",notesAdapter.getItem(position));
        newIntent.putExtra("EDIT_NOTE",true);
        startActivityForResult(newIntent, NOTE_REQUEST_CODE);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));


        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String newText)
            {
                notesAdapter.getFilter().filter(newText);
                System.out.println("on text chnge text: "+newText);
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                notesAdapter.getFilter().filter(query);
                System.out.println("on query submit: "+query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);

        return true;
    }

}
