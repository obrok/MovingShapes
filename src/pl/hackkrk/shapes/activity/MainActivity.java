package pl.hackkrk.shapes.activity;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import pl.hackkrk.shapes.R;
import pl.hackkrk.shapes.view.ShapeView;

public class MainActivity extends Activity implements View.OnTouchListener {
    ShapeView shapeView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        shapeView = (ShapeView) findViewById(R.id.shape_view);
        shapeView.setPolygon(
                new Point(10, 20),
                new Point(100, 120),
                new Point(10, 120)
        );
        shapeView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if(event.getPointerCount() == 3){
            Log.d("Touchy", event.toString());
            Point[] points = new Point[3];
            for (int i = 0; i < event.getPointerCount(); i++){
                points[i] = new Point((int)event.getX(i), (int)event.getY(i));
            }

            shapeView.setPolygon(points);
        }
        return true;
    }
}
