package noteapplication.scribble.com.scribble;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SimpleSwipeUndoAdapter;

import java.util.ArrayList;

import adapters.ArtsAdapter;
import db.DatabaseHandler;
import modelObjects.Arts;

/**
 * Created by rsampath on 7/31/15.
 */
public class ViewArtActivity extends BaseActivity {
    private DynamicListView listViewArts;
    private DatabaseHandler db;
    private ArrayList<Arts> listOfArts;
    private ArtsAdapter artsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        navDrawerIcon = R.drawable.note_app24x24;
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_view_arts, frameLayout);
        listViewArts = (DynamicListView) findViewById(R.id.list_view_arts);
        db = new DatabaseHandler(this);
        listOfArts = new ArrayList<>();
        listOfArts = (ArrayList<Arts>) db.getAllArt();
        artsAdapter = new ArtsAdapter(ViewArtActivity.this, 0, listOfArts);
        listViewArts.setAdapter(artsAdapter);
        listViewArts.setTextFilterEnabled(true);

        listViewArts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleOnItemOnSettings(position);
            }
        });

        SimpleSwipeUndoAdapter swipeUndoAdapter = new SimpleSwipeUndoAdapter(artsAdapter, ViewArtActivity.this,
                new OnDismissCallback() {
                    @Override
                    public void onDismiss(@NonNull final ViewGroup listView, @NonNull final int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            db.deleteArt(artsAdapter.getItem(position));
                            artsAdapter.remove(position);
                            artsAdapter.notifyDataSetChanged();
                        }
                    }
                }
        );
        swipeUndoAdapter.setAbsListView(listViewArts);
        listViewArts.setAdapter(swipeUndoAdapter);
        listViewArts.enableSimpleSwipeUndo();

        menu = (FloatingActionsMenu) findViewById(R.id.add_actions);
        addArt = (FloatingActionButton) findViewById(R.id.action_add_art);
        addArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(ViewArtActivity.this, AddArtActivity.class);
                newIntent.putExtra("EDIT_NOTE", false);
                startActivityForResult(newIntent, ART_REQUEST_CODE);
            }
        });

        addNote = (FloatingActionButton) findViewById(R.id.action_add_note);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(ViewArtActivity.this, AddNoteActivity.class);
                startActivityForResult(newIntent, NOTE_REQUEST_CODE);
            }
        });
    }

    @Override
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.art);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            menu.collapse();
            switch (requestCode) {
                case ART_REQUEST_CODE:
                    if (data != null) {
                        listOfArts.clear();
                        listOfArts.addAll(db.getAllArt());
                        artsAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    }

    private void handleOnItemOnSettings(int position) {
        Intent newIntent = new Intent(ViewArtActivity.this, AddArtActivity.class);
        newIntent.putExtra("ARTS_OBJECT", artsAdapter.getItem(position));
        newIntent.putExtra("EDIT_ART", true);
        startActivityForResult(newIntent, ART_REQUEST_CODE);
    }

}
