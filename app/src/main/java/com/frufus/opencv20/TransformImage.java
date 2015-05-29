package com.frufus.opencv20;

import android.net.Uri;

import org.opencv.*;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;

public class TransformImage {

    private Mat image;


    public TransformImage(File picture){


        image = Imgcodecs.imread(picture.getAbsolutePath().toString());


    }
}
