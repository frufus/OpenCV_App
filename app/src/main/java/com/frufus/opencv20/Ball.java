package com.frufus.opencv20;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Rosie on 09.07.2015.
 */
public class Ball {

    final static int DEFAULT_RADIUS = 10;


    private float positionX;
    private float positionY;
    private Paint paint;
    private int moveX = 0;
    private int moveY = 0;
    private int speed = 2;


    public Ball(){
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
    }

    public void drawBall(Canvas canvas){
        canvas.drawCircle(positionX, positionY, DEFAULT_RADIUS, paint);
    }

    public void setPositionBall(float x, float y){
        positionX = x;
        positionY = y;
    }
    public void setBallMovementDirectionX(int i){
        moveX = i;
    }
    public void setBallMovementDirectionY(int i){
        moveY = i;
    }

    public void updateMovementBall(Canvas canvas){

        updateMovementBall(canvas.getWidth(),canvas.getHeight());
    }
    public void updateMovementBall(int canvasWidth, int canvasHeight){

        if (moveX == 1 && moveY == 0){
            positionX +=speed;
        }
        else if(moveX == 2 && moveY == 0){
            positionX -=speed;
        }
        else if(moveY == 1 && moveX == 0){
            positionY +=speed;
        }
        else if(moveY == 2 && moveX == 0){
            positionY -=speed;
        }
        else if(moveX == 1 && moveY == 1){
            positionX+= speed;
            positionY+= speed;
        }
        else if(moveX == 1 && moveY == 2){
            positionX += speed;
            positionY -= speed;
        }
        else if(moveX == 2 && moveY == 2){
            positionX -= speed;
            positionY -= speed;
        }

        else if(moveX == 2 && moveY == 1){
            positionX -= speed;
            positionY += speed;
        }


       /* positionY +=speedY;

        if(positionX - DEFAULT_RADIUS < 0){

                speedX *=-1;
                positionX = DEFAULT_RADIUS;



        }
        else if(positionX + DEFAULT_RADIUS > canvasWidth){
            speedX *=-1;
            positionX = canvasWidth - DEFAULT_RADIUS;
        }
        else if(positionY - DEFAULT_RADIUS < 0){
            speedY *=-1;
            positionY = DEFAULT_RADIUS;
        }
        else if(positionY + DEFAULT_RADIUS > canvasHeight){
            speedY +=-1;
            positionY = canvasHeight - DEFAULT_RADIUS;
        }*/
    }
    public float getPositionX(){
        return positionX;
    }
    public  float getPositionY(){
        return positionY;
    }

}
