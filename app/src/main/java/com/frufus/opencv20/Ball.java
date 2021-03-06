package com.frufus.opencv20;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * setzbar wenn man ein Objekt der Klasse erzeugt sind folgende Dinge einstellbar:
 *  - Position setzen: ball.setPositionBall(float x, float y)
 *  - Position ausgeben:    ball.getPositionX()
 *                          ball.getPositionY()
 *  - Bewegung in x- oder y- Richtung:  ball.setBallMovementDirectionX(int i)
 *                                      ball.setBallMovementDirectionY(int i)
 *              --> welche Integer siehe Methode
 *  - Bewegung anzeigen lassen: ball.updateMovementBall(Canvas canvas)
 *  - Farbe: ball.setRadiusBall(int r)
 *  - Radius: ball.setRadiusBall(int r)
 *
 */
public class Ball {

    private int radius = 10;


    private float positionX;
    private float positionY;
    private Paint paint;
    private int moveX = 0;
    private int moveY = 0;
    private int speed = 2;
    private int color = Color.BLUE;
    private boolean canMoveRight = true;
    private boolean canMoveLeft = true;
    private boolean canMoveUp = true;
    private boolean canMoveDown = true;

    public Ball(){
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

    }
    private void setColor(){
        paint.setColor(color);
    }

    public void setRadiusBall(int r){
        radius = r;
    }
    public void setColorBall(int c){
        color = c;
    }
    public void drawBall(Canvas canvas){
        setColor();
        canvas.drawCircle(positionX, positionY, radius, paint);
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

        /*
        Hier wird die Bewegung des Balles berechnen und die Bewegunsrichtung festgelegt

         In x-Richtung:
            - links = 2;
            - rechts = 1;
         In y-Richtung:
            - unten = 1;
            - oben = 2;
         Ruhezustand = 0
        */
        if (moveX == 1 && moveY == 0 && canMoveRight){
            positionX +=speed;
        }
        if(moveX == 2 && moveY == 0 && canMoveLeft){
            positionX -=speed;
        }
        if(moveY == 1 && moveX == 0 && canMoveDown){
            positionY +=speed;
        }
        if(moveY == 2 && moveX == 0 && canMoveUp){
            positionY -=speed;
        }
        if(moveX == 1 && moveY == 1){
            if(canMoveRight) positionX+= speed;
            if(canMoveDown) positionY+= speed;
        }
        if(moveX == 1 && moveY == 2){
            if(canMoveRight) positionX += speed;
            if(canMoveUp) positionY -= speed;
        }
        if(moveX == 2 && moveY == 2){
            if(canMoveLeft)positionX -= speed;
            if(canMoveUp) positionY -= speed;
        }

        if(moveX == 2 && moveY == 1){
            if(canMoveLeft) positionX -= speed;
            if(canMoveDown) positionY += speed;
        }



        // Kollisionsabfrage mit dem Canvasrand
        if(positionX - radius < 0){

                positionX = radius;

        }
        else if(positionX + radius > canvasWidth){

            positionX = canvasWidth - radius;
        }
        else if(positionY - radius < 0){

            positionY = radius;
        }
        else if(positionY + radius > canvasHeight){

            positionY = canvasHeight - radius;
        }
    }
    public void setCanMoveRight(boolean r){
        canMoveRight = r;
    }
    public void setCanMoveLeft(boolean r){
        canMoveLeft = r;
    }
    public void setCanMoveUp(boolean r){
        canMoveUp = r;
    }
    public void setCanMoveDown(boolean r){
        canMoveDown = r;
    }
    public float getPositionX(){
        return positionX;
    }
    public  float getPositionY(){
        return positionY;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public int getRadius() {
        return radius;
    }
}
