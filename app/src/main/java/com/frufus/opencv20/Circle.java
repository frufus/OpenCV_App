package com.frufus.opencv20;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Circle extends View{
    Paint paint;

    public  Circle(Context context){
        super(context);
        init();
    }

    private  void init(){
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10f);
        paint.setStyle(Paint.Style.STROKE);
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.drawCircle(50f,50f,30,paint);
    }
}
