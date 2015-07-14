package com.frufus.opencv20;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;

import org.opencv.core.Mat;

public class Grid {

    private Paint paint;
    private Region gridRegion;

    public Grid() {

        paint = new Paint();
        gridRegion = new Region();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
    }

    public void drawGrid(Canvas canvas, Mat lines) {
        for (int x = 0; x < lines.rows(); x++){

            double[] vec = lines.get(x,0);
            float x1 = (float) vec[0];
            float y1 = (float) vec[1];
            float x2 = (float) vec[2];
            float y2 = (float) vec[3];


           canvas.drawLine(x1,y1, x2, y2, paint);
        }
    }
}
