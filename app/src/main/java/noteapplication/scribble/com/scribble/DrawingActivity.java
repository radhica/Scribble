package noteapplication.scribble.com.scribble;

/**
 * Created by rsampath on 7/24/15.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class DrawingActivity extends Activity {

    private CanvasView customCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_activity);

        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
    }

    public void clearCanvas(View v) {
        customCanvas.clearCanvas();
    }

}