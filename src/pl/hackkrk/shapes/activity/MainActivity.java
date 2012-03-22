package pl.hackkrk.shapes.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import pl.hackkrk.shapes.R;
import pl.hackkrk.shapes.view.ShapeView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

public class MainActivity extends Activity implements SensorEventListener {
    public static final UUID GAME_UUID = UUID.fromString("6d511540-7458-11e1-b0c4-0800200c9a66");
    private SensorManager sensorManager;
    private Sensor sensor;

    FloatPoint point = new FloatPoint(200, 300);
    private ShapeView shapeView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        shapeView = (ShapeView) findViewById(R.id.shape_view);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

        try {
            BluetoothServerSocket socket = adapter.listenUsingRfcommWithServiceRecord("game", GAME_UUID);
            new GameThread(socket).start();
        } catch (IOException e) {
            Log.e("Errory", "fail", e);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    public static class FloatPoint {
        FloatPoint(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float x, y;
    }

    private void redraw() {
        if (point != null) {
            shapeView.setLocation(point);
        }
    }

    private void move(float xMovement, float yMovement) {
        point.x += xMovement;
        point.y += yMovement;
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

    private class GameThread extends Thread {
        private BluetoothServerSocket socket;

        public GameThread(BluetoothServerSocket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BluetoothSocket clientSocket = socket.accept();
                InputStream stream = clientSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                while(true){
                    String line = reader.readLine();
                    Log.d("Line", line);
                }
            } catch (IOException e) {
                Log.e("No worky", "", e);
            }
        }
    }
}
