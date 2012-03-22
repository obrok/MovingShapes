package pl.hackkrk.shapes.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ShapeView extends View {
    Paint paint = new Paint();

    public ShapeView(Context context) {
        super(context);
    }

    public ShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShapeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.WHITE);
        canvas.drawLine(0, 0, 10, 10, paint);
    }
}
