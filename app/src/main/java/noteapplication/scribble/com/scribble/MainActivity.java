package noteapplication.scribble.com.scribble;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SimpleSwipeUndoAdapter;

import java.util.ArrayList;

import adapters.NotesAdapter;
import db.DatabaseHandler;
import modelObjects.Notes;


public class MainActivity extends BaseActivity {
    private DynamicListView listViewNotes;
    private NotesAdapter notesAdapter = null;

    private ArrayList<Notes> listOfNotes;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        navDrawerIcon = R.drawable.note_app24x24;
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);
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

        SimpleSwipeUndoAdapter swipeUndoAdapter = new SimpleSwipeUndoAdapter(notesAdapter, MainActivity.this,
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
        swipeUndoAdapter.setAbsListView(listViewNotes);
        listViewNotes.setAdapter(swipeUndoAdapter);
        listViewNotes.enableSimpleSwipeUndo();


        menu = (FloatingActionsMenu) findViewById(R.id.add_actions);
        addArt = (FloatingActionButton) findViewById(R.id.action_add_art);
        addArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainActivity.this, AddArtActivity.class);
                newIntent.putExtra("EDIT_NOTE", false);
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
                    if (data != null) {
                        boolean noteChanged = (boolean) data.getSerializableExtra("NOTE_CHANGED");
                        if (noteChanged) {
                            listOfNotes.clear();
                            listOfNotes.addAll(db.getAllNotes());
                            notesAdapter.notifyDataSetChanged();
                        }
                    }
                    break;
            }
        }
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

        searchView.setSubmitButtonEnabled(true);

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                notesAdapter.getFilter().filter(newText);
                System.out.println("on text chnge text: " + newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                notesAdapter.getFilter().filter(query);
                System.out.println("on query submit: " + query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
        restoreActionBar();
        return true;
    }

    @Override
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.note);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void handleOnItemOnSettings(int position) {
        Intent newIntent = new Intent(MainActivity.this, AddNoteActivity.class);
        newIntent.putExtra("NOTES_OBJECT", notesAdapter.getItem(position));
        newIntent.putExtra("EDIT_NOTE", true);
        startActivityForResult(newIntent, NOTE_REQUEST_CODE);

    }
}
