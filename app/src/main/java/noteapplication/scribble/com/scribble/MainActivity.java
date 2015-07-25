package noteapplication.scribble.com.scribble;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private static final int REQUEST_CODE = 1000;
    private static final int RESULT_CODE = 1001;
    private ListView listViewNotes;
    private NotesAdapter notesAdapter;

    private ArrayList<Notes>  listOfNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listOfNotes = new ArrayList<Notes>();
        listViewNotes = (ListView) findViewById(R.id.listview_notes);
        listOfNotes.add(new Notes("Note 1","abcd efgh ijkl. hgsij!!"));
        listOfNotes.add(new Notes("Note 2","wkhfai!n7r9wur w8tow \n hdfhjsdhvuid"));

       
        notesAdapter = new NotesAdapter(MainActivity.this,0,listOfNotes);
        listViewNotes.setAdapter(notesAdapter);

        final FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.action_add_note);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainActivity.this,NoteDetailActivity.class);
                startActivityForResult(newIntent, REQUEST_CODE);

            }
        });


        final FloatingActionButton actionB = (FloatingActionButton) findViewById(R.id.action_draw);
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainActivity.this,DrawingActivity.class);
                startActivity(newIntent);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case REQUEST_CODE:
                    if (data != null) {
                        listOfNotes.add((Notes) data.getParcelableExtra("NEW_NOTE"));
                        notesAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    }


}
