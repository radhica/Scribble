package noteapplication.scribble.com.scribble;

/**
 * Created by rsampath on 7/24/15.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import UIcontrols.CanvasView;
import db.DatabaseHandler;
import modelObjects.Arts;

public class AddArtActivity extends ActionBarActivity {

    private static final String TAG = AddArtActivity.class.getCanonicalName();
    private CanvasView customCanvas;
    private EditText artTitle;
    private DatabaseHandler db;
    private Arts art;
    private Intent intent;
    private boolean enteredTitle;
    private boolean editArt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_art_activity);

        db = new DatabaseHandler(this);

        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
        artTitle = (EditText) findViewById(R.id.art_title);

        intent = getIntent();
        if (intent.getExtras() != null) {
            if (intent.getExtras().containsKey("EDIT_ART")) {
                art = (Arts) intent.getSerializableExtra("ARTS_OBJECT");
                Arts arts = db.getArt(art.getId());
                artTitle.setText(arts.getTitle());
                customCanvas.setBackgroundDrawable(new BitmapDrawable(arts.getBitmap()));
                editArt = true;
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

                View content = customCanvas;
                content.setDrawingCacheEnabled(true);
                content.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                Bitmap bitmap = content.getDrawingCache();

                if(enteredTitle) {
                    if(artTitle.getText().toString().length() == 0){
                        artTitle.setError("ERROR");
                    } else if(editArt) {
                        art.setBitmap(bitmap);
                        art.setTitle(artTitle.getText().toString());
                        art.setLastModified(new SimpleDateFormat("MMM dd, yyyy h:mm a").format(new Timestamp(System.currentTimeMillis())));
                        db.updateArt(art);
                        setResult(RESULT_OK, backIntent);
                        finish();
                    }
                    else{
                        art = new Arts(artTitle.getText().toString(), bitmap);
                        art.setLastModified(new SimpleDateFormat("MMM dd, yyyy h:mm a").format(new Timestamp(System.currentTimeMillis())));
                        db.addArt(art);
                        setResult(RESULT_OK, backIntent);
                        finish();
                    }
                } else {
                    artTitle.setVisibility(View.VISIBLE);
                    enteredTitle = true;

                }
                return true;
            case R.id.action_discard:
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

    public void clearCanvas(View v) {
        customCanvas.clearCanvas();
    }


}