package pl.hackkrk.shapes.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import pl.hackkrk.shapes.R;
import pl.hackkrk.shapes.view.ShapeView;

public class MainActivity extends Activity implements View.OnTouchListener, SensorEventListener {
    public static final int HIT_BOX = 40;
    ShapeView shapeView;
    private SensorManager sensorManager;
    private Sensor sensor;
    private FloatPoint grabbedPoint;
    private long drawingQuad;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        shapeView = (ShapeView) findViewById(R.id.shape_view);
        shapeView.setOnTouchListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    Point twoFingerStart = null;

    public static class FloatPoint {
        FloatPoint(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float x, y;
    }

    FloatPoint[] points = new FloatPoint[0];

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int count = event.getPointerCount();

        if (count == 1 && event.getAction() != MotionEvent.ACTION_UP) {
            if (grabbedPoint != null) {
                grabbedPoint.x = event.getX();
                grabbedPoint.y = event.getY();
            } else {
                grabbedPoint = findClosesPoint(event);
            }
        } else {
            grabbedPoint = null;
        }

        if (count == 2) {
            if (shouldTranslate(event)) {
                float xMovement = event.getX() - twoFingerStart.x;
                float yMovement = event.getY() - twoFingerStart.y;
                move(xMovement, yMovement);
            }

            twoFingerStart = new Point((int) event.getX(), (int) event.getY());
        } else {
            twoFingerStart = null;
        }

        if (count == 4 || (count == 3 && System.currentTimeMillis() - drawingQuad > 1000)) {
            points = new FloatPoint[count];
            for (int i = 0; i < count; i++) {
                points[i] = new FloatPoint(event.getX(i), event.getY(i));
            }
        }

        if (count == 4) {
            drawingQuad = System.currentTimeMillis();
        }

        redraw();

        return true;
    }

    private FloatPoint findClosesPoint(MotionEvent event) {
        for (FloatPoint point : points) {
            if (Math.abs(point.x - event.getX()) <= HIT_BOX && Math.abs(point.y - event.getY()) <= HIT_BOX) {
                return point;
            }
        }

        return null;
    }

    private void redraw() {
        if (points.length > 2) {
            shapeView.setPolygon(points);
        }
    }

    private void move(float xMovement, float yMovement) {
        for (FloatPoint point : points) {
            point.x += xMovement;
            point.y += yMovement;
        }
    }

    private boolean shouldTranslate(MotionEvent event) {
        double maxX = Integer.MIN_VALUE;
        double maxY = Integer.MIN_VALUE;
        double minX = Integer.MAX_VALUE;
        double minY = Integer.MAX_VALUE;

        for (FloatPoint point : points) {
            if (point.x > maxX) maxX = point.x;
            if (point.x < minX) minX = point.x;
            if (point.y > maxY) maxY = point.y;
            if (point.y < minY) minY = point.y;
        }

        return twoFingerStart != null &&
                event.getX() < maxX && event.getX() > minX &&
                event.getY() < maxY && event.getY() > minY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float yMovement = event.values[1] * 10;
        float xMovement = event.values[0] * -10;
        if (Math.abs(yMovement) > 0.1 && Math.abs(xMovement) > 0.1) {
            move(xMovement, yMovement);
            redraw();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
