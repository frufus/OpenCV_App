package com.frufus.opencv20;
import android.app.Application;

import android.content.Context;

import org.opencv.core.Mat;


public class BaseApp extends Application {

    private Context context;
    private Mat lines;

    @Override
    public void onCreate() {
        super.onCreate();

        this.context = getApplicationContext();

    }

    public Mat getLines() {
        return lines;
    }

    public void setLines(Mat lines) {
        this.lines = lines;
    }
}
