package com.frufus.opencv20;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

public class PlayActivity extends Activity {
    private String filePathPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Intent getPlayButtonIntent = getIntent();
        filePathPicture = getPlayButtonIntent.getExtras().getString("File_Path");

        Bitmap bmap = BitmapFactory.decodeFile(filePathPicture);
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        Canvas canvas = new Canvas(bmap);
        canvas.drawBitmap(bmap, 70,70,p);


    }

}
