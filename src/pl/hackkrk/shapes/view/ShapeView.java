package pl.hackkrk.shapes.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import pl.hackkrk.shapes.activity.MainActivity;

public class ShapeView extends View {
    Paint paint = new Paint();
    Path path = new Path();
    private MainActivity.FloatPoint location;

    public ShapeView(Context context) {
        super(context);
    }

    public ShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShapeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setLocation(MainActivity.FloatPoint location) {
        this.location = location;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setColor(Color.argb(255, 0, 123, 242));

        canvas.drawCircle(location.x, location.y, 20, paint);
    }
}
