package noteapplication.scribble.com.scribble;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;

import java.util.ArrayList;

import adapters.ArtsAdapter;
import db.DatabaseHandler;
import modelObjects.Arts;

/**
 * Created by rsampath on 7/31/15.
 */
public class ViewArtActivity extends BaseActivity{
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
              //  handleOnItemOnSettings(position);
                Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_LONG).show();
            }
        });

        listViewArts.enableSwipeToDismiss(
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
    }

    @Override
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.art);
    }

}
