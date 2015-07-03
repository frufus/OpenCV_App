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

    private float positionX;
    private float positionY;

    private  int RADIUS = 50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawingView(this));

        Intent getPlayButtonIntent = getIntent();
        filePathPicture = getPlayButtonIntent.getExtras().getString("File_Path");





    }

    class DrawingView extends SurfaceView {



        private final SurfaceHolder surfaceHolder;
        private final Paint paint = new Paint();


        public DrawingView(Context context) {
            super(context);
            surfaceHolder = getHolder();

            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);

        }


        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                if (surfaceHolder.getSurface().isValid()) {
                    Canvas canvas = surfaceHolder.lockCanvas();

                    canvas.drawRGB(100,100,100);
                    positionX = event.getX();
                    positionY = event.getY();
                    canvas.drawCircle(positionX, positionY, RADIUS, paint);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            return false;
        }


    }
}
