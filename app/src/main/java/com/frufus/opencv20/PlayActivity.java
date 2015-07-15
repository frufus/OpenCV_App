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
import android.widget.Toast;

import org.opencv.core.Mat;

public class PlayActivity extends Activity {
    private String filePathPicture;

    private Float positionX = 20f;
    private Float positionY = 20f;


    private boolean createFinishPoint = true;
    private boolean createStartPoint = false;
    private boolean isPointStartSet = false;
    private boolean isPointFinishSet = false;

    private Float accelerationX;
    private Float accelerationY;
    private Ball startBall = new Ball();
    private Ball finishBall = new Ball();

    int movementX;
    int movementY;

    private boolean won = false;
    BaseApp baseApp;
    Boolean gridDrawn = false;
    String tag = "PlayActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawingView(this));
        baseApp = (BaseApp) getApplicationContext();
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

            startBall.setBallMovementDirectionX(links);
            movementX = -1;

        }

        else if(accelerationX< -1 ){

            startBall.setBallMovementDirectionX(rechts);
            movementX = 1;

        }
        else if(accelerationY < -1){

            startBall.setBallMovementDirectionY(oben);
            movementY = 1;

        }
        else if(accelerationY > 1 ){

            startBall.setBallMovementDirectionY(unten);
            movementY = -1;
        }

        else{

            startBall.setBallMovementDirectionX(0);
            startBall.setBallMovementDirectionY(0);
            movementX = 0;
            movementY = 0;
        }



        //Log.d(tag,"x : "+ accelerationX.toString());
        //Log.d(tag,"y : "+ accelerationY.toString());

    }

    class DrawingView extends SurfaceView {

        private final Paint paint = new Paint();
        Grid grid = new Grid();

        public DrawingView(Context context) {
            super(context);

            this.setWillNotDraw(false);
            paint.setColor(Color.YELLOW);
            paint.setStyle(Paint.Style.FILL);
            //ball.setPositionBall(300f, 300f);
            //ball.setColorBall(Color.CYAN);
            //ball.setRadiusBall(50);

            finishBall.setColorBall(Color.GREEN);
            finishBall.setRadiusBall(50);
            startBall.setColorBall(Color.RED);



        }
        @Override
        protected void onDraw(Canvas canvas) {
            //super.onDraw(canvas);
            canvas.drawRGB(100, 100, 100);
            grid.drawGrid(canvas, baseApp.getLines());
            if(isPointFinishSet && isPointStartSet){


                if(!lineCollision()){
                    startBall.updateMovementBall(canvas);
                }

                finishBall.drawBall(canvas);
                startBall.drawBall(canvas);

                if(onFinishCollision()) {
                    canvas.drawRGB(0, 100, 0);
                }
            }
            onCollision();
            if(won){
                canvas.drawCircle(50,50,100,paint);
            }

            invalidate();
        }



        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {

                    positionX = event.getX();
                    positionY = event.getY();

                   if(createFinishPoint){
                        finishBall.setPositionBall(positionX,positionY);
                        createStartPoint = true;
                        createFinishPoint = false;
                       isPointFinishSet = true;

                    }
                    else if (createStartPoint){
                        startBall.setPositionBall(positionX,positionY);
                        createStartPoint = false;
                       isPointStartSet = true;
                    }
                    // Log.d(tag, positionX.toString());
                    //startBall.setPositionBall(positionX,positionY);
            }
            return false;
        }

        private boolean lineCollision(){
            Mat lines = baseApp.getLines();
            float x = startBall.getPositionX();
            float y = startBall.getPositionY();

            startBall.setCanMoveRight(true);
            startBall.setCanMoveLeft(true);
            startBall.setCanMoveDown(true);
            startBall.setCanMoveUp(true);

            for (int i = 0; i < lines.rows(); i++){

                double[] vec = lines.get(i,0);
                float x1 = (float) vec[0];
                float y1 = (float) vec[1];
                float x2 = (float) vec[2];
                float y2 = (float) vec[3];

                if((x > x1 && x < x2 && y > y1 && y < y2) || (x < x1 && x > x2 && y < y1 && y > y2) ){
                    if(movementX > 0){
                        startBall.setCanMoveRight(false);
                    } else if(movementX < 0){
                        startBall.setCanMoveLeft(false);
                    }
                    if(movementY > 0){
                        startBall.setCanMoveUp(false);
                    } else if(movementY < 0){
                        startBall.setCanMoveDown(false);
                    }
                    return true;
                }

            }
            return false;
        }

        private boolean onFinishCollision() {
            float x1 = startBall.getPositionX();
            float y1 = startBall.getPositionY();
            int r1 = startBall.getRadius();

            float x2 = finishBall.getPositionX();
            float y2 = finishBall.getPositionY();
            int r2 = finishBall.getRadius();

            if(Math.pow(x1-x2, 2) + Math.pow(y1 - y2, 2) <= Math.pow(r1+r2, 2)){
                return true;
            }
            return false;
        }

        private void onCollision(){

            Float finishX = finishBall.getPositionX();
            Float finishY = finishBall.getPositionY();
            Float startX = startBall.getPositionX();
            Float startY = startBall.getPositionY();
            Log.d(tag,"y: "+ startY.toString());

            if (startX + 10f <finishX+50f && startX -10f >finishX - 50f){
                if(startY + 10f < finishY +50f && startY - 10f > finishY +50f) {
                    won = true;
                }
            }
        }


    }
}
