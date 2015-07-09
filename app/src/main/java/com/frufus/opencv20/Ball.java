package com.frufus.opencv20;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by Rosie on 09.07.2015.
 */
public class Ball {

    final static int DEFAULT_RARIUS = 10;


    private float positionX;
    private float positionY;
    private Paint paint;

    public Ball(){
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
    }

    public void drawBall(Canvas canvas){
        canvas.drawCircle(positionX,positionY,DEFAULT_RARIUS,paint);
    }

    public void setPositionBall(float x, float y){
        positionX = x;
        positionY = y;
    }
    public float getPositionX(){
        return positionX;
    }
    public  float getPositionY(){
        return positionY;
    }

}
