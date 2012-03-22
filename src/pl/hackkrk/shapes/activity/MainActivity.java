package pl.hackkrk.shapes.activity;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import pl.hackkrk.shapes.R;
import pl.hackkrk.shapes.view.ShapeView;

public class MainActivity extends Activity
{
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
    }
}
