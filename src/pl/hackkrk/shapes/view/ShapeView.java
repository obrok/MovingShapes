package pl.hackkrk.shapes.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import pl.hackkrk.shapes.activity.MainActivity;

import java.util.Arrays;
import java.util.Comparator;

public class ShapeView extends View {
    Paint paint = new Paint();
    Path path = new Path();

    public ShapeView(Context context) {
        super(context);
    }

    public ShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShapeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setPolygon(MainActivity.FloatPoint... locations) {
        double midX = 0;
        double midY = 0;
        for (MainActivity.FloatPoint location : locations) {
            midX += location.x;
            midY += location.y;
        }
        midX /= locations.length;
        midY /= locations.length;

        final double finalMidX = midX;
        final double finalMidY = midY;
        Arrays.sort(locations, 0, locations.length, new Comparator<MainActivity.FloatPoint>() {
            @Override
            public int compare(MainActivity.FloatPoint point, MainActivity.FloatPoint point1) {
                Double t1 = Math.atan2(point.y - finalMidY, point.x - finalMidX);
                Double t2 = Math.atan2(point1.y - finalMidY, point1.x - finalMidX);
                return t1.compareTo(t2);
            }
        });

        path = new Path();
        MainActivity.FloatPoint first = locations[0];
        path.moveTo(first.x, first.y);

        for (int i = 1; i < locations.length; i++) {
            MainActivity.FloatPoint location = locations[i];
            path.lineTo(location.x, location.y);
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setColor(Color.argb(255, 0, 123, 242));

        canvas.drawPath(path, paint);
    }
}
