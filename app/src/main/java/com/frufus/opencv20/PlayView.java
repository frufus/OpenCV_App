package com.frufus.opencv20;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.util.Log;
import android.view.View;



public class PlayView extends View{
    private Paint paint;
    private Paint p;
    private String filePathPicture;
    private Bitmap bitmap;



    public PlayView(Context context){
        super(context);

        bitmap = BitmapFactory.decodeFile(filePathPicture);

        init();

       /* bmap = BitmapFactory.decodeFile(pathPicture);

        p = new Paint();
        p.setColor(Color.WHITE);
        Canvas canvas = new Canvas(bmap);
        canvas.drawBitmap(bmap, 70,70,p);*/
    }

    public void setFilePath(String filePath){
        filePathPicture = filePath;

    }
    public String getFilePath(String filePath){
        return filePath;
    }



    private  void init(){
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL);

        p = new Paint();
        p.setColor(Color.WHITE);
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

       // canvas.drawBitmap(bitmap,30,30,p);
        canvas.drawCircle(50,50,30,paint);
    }
}
