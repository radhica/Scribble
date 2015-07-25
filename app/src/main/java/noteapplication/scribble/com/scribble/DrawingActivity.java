package noteapplication.scribble.com.scribble;

/**
 * Created by rsampath on 7/24/15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import UIcontrols.CanvasView;

public class DrawingActivity extends ActionBarActivity {

    private CanvasView customCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_activity);
        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent backIntent = new Intent();
        switch (item.getItemId()) {
            case R.id.action_save:
                setResult(RESULT_OK, backIntent);
                finish();
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