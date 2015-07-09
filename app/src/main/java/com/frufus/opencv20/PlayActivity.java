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

    private Float accelerationX;
    private Float accelerationY;
    private Ball ball = new Ball();
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


        accelerationX = e.values[0];
        accelerationY = e.values[1];


        int links = 2;
        int rechts = 1;
        int unten = 1;
        int oben = 2;
        if(accelerationX> 1){

            ball.setBallMovementDirectionX(links);

        }

        else if(accelerationX< -1 ){

            ball.setBallMovementDirectionX(rechts);
        }
        else if(accelerationY < -1){

            ball.setBallMovementDirectionY(oben);
        }
        else if(accelerationY > 1 ){

            ball.setBallMovementDirectionY(unten);
        }

        else{
            ball.setBallMovementDirectionX(0);
            ball.setBallMovementDirectionY(0);
        }



        Log.d(tag,"x : "+ accelerationX.toString());
        Log.d(tag,"y : "+ accelerationY.toString());

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
            canvas.drawRGB(100, 100, 100);


            ball.updateMovementBall(canvas);
            ball.drawBall(canvas);

            canvas.drawCircle(positionX, positionY, RADIUS, paint);
            invalidate();
        }



        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {

                    positionX = event.getX();
                    positionY = event.getY();


                    // Log.d(tag, positionX.toString());

            }
            return false;
        }




    }
}
