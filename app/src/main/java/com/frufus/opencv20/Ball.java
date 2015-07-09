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
    private int speedX = 10;
    private int speedY = 10;

    public Ball(){
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
    }

    public void drawBall(Canvas canvas){
        canvas.drawCircle(positionX, positionY, DEFAULT_RARIUS, paint);
    }

    public void setPositionBall(float x, float y){
        positionX = x;
        positionY = y;
    }

    public void updateMovementBall(Canvas canvas){

        updateMovementBall(canvas.getWidth(),canvas.getHeight());
    }
    public void updateMovementBall(int canvasWidth, int canvasHeight){
        positionX +=speedX;
        positionY +=speedY;

        if(positionX - DEFAULT_RARIUS < 0){
            speedX *=-1;
            positionX = DEFAULT_RARIUS;
        }
        else if(positionX + DEFAULT_RARIUS > canvasWidth){
            speedX *=-1;
            positionX = canvasWidth - DEFAULT_RARIUS;
        }
        else if(positionY - DEFAULT_RARIUS < 0){
            speedY *=-1;
            positionY = DEFAULT_RARIUS;
        }
        else if(positionY + DEFAULT_RARIUS> canvasHeight){
            speedY +=-1;
            positionY = canvasHeight - DEFAULT_RARIUS;
        }
    }
    public float getPositionX(){
        return positionX;
    }
    public  float getPositionY(){
        return positionY;
    }

}
