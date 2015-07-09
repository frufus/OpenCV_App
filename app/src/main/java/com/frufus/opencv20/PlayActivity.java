package com.frufus.opencv20;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

public class PlayActivity extends Activity {
    private String filePathPicture;

    private Float positionX = 20f;
    private Float positionY = 20f;

    private  int RADIUS = 50;
    private float[] gravity = new float[3];
    private float[] linear_acceleration = new float[3];
    private Double accelerationX;
    private Double accelerationY;
    Ball ball = new Ball();
    String tag = "PlayActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawingView(this));

        Intent getPlayButtonIntent = getIntent();
        filePathPicture = getPlayButtonIntent.getExtras().getString("File_Path");

        initSensors();





    }
    private  void initSensors(){
        SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent e) {
                handleSensorEvents(e);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);

    }
    private void handleSensorEvents(SensorEvent e){
        if (e.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            onAccelerometerChanged(e);
        }
    }

    private void onAccelerometerChanged(SensorEvent e){

        // Hier kommt ein Hochpassfilter um die Erdbeschleunigung herauszurechnen
        final float alpha = 0.8f;

        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * e.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * e.values[1];


        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = e.values[0] - gravity[0];
        linear_acceleration[1] = e.values[1] - gravity[1];

        Float accX =linear_acceleration[0];
        Float accY = linear_acceleration[1];

        accelerationX = accX.doubleValue();
        accelerationY = accY.doubleValue();


       // Log.d(tag,"x : "+ accelerationX.toString());
        //Log.d(tag, accelerationY.toString());
    }

    class DrawingView extends SurfaceView {



        private final SurfaceHolder surfaceHolder;
        private final Paint paint = new Paint();


        public DrawingView(Context context) {
            super(context);
            surfaceHolder = getHolder();
            this.setWillNotDraw(false);
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            ball.setPositionBall(300f, 300f);
        }
        @Override
        protected void onDraw(Canvas canvas) {
            //super.onDraw(canvas);
            canvas.drawRGB(100,100,100);


            ball.updateMovementBall(canvas);
            ball.drawBall(canvas);
            canvas.drawCircle(positionX, positionY, RADIUS, paint);
            invalidate();
        }



        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                /* if (surfaceHolder.getSurface().isValid()) {
                    Canvas canvas = surfaceHolder.lockCanvas();

                  canvas.drawRGB(100,100,100);*/
                    positionX = event.getX();
                    positionY = event.getY();
                  //  canvas.drawCircle(positionX, positionY, RADIUS, paint);

                    Log.d(tag, positionX.toString());
                   /* ball.setPositionBall(150f, 150f);
                    ball.drawBall(canvas);
                    Float p = ball.getPositionX();
                    Log.d(tag,"position x : " + p.toString());

                    surfaceHolder.unlockCanvasAndPost(canvas);*/
               // }
            }
            return false;
        }




    }
}
