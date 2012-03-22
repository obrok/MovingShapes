package pl.hackkrk.shapes.view;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

public class ShapeView extends View {
    Paint paint = new Paint();
    Path path;

    public ShapeView(Context context) {
        super(context);
    }

    public ShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShapeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setPolygon(Point... locations){
        path = new Path();
        Point first = locations[0];
        path.moveTo(first.x, first.y);
        for (int i = 1; i < locations.length; i++) {
            Point location = locations[i];
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
