package com.frufus.opencv20;




import android.graphics.Bitmap;

//import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.android.Utils;

import java.io.File;

public class TransformImage {

    private Mat image;
    private Mat lines;
    private Mat imageWithLines;
    private Point startLine;
    private Point endLine;

    private double x1, x2, y1, y2;
    private Bitmap pBitmap;




    public TransformImage(File picture){
        String filePath = picture.getAbsolutePath().toString();
        lines = new Mat();

        image = Imgcodecs.imread(filePath);
        imageWithLines = image.clone();
        Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY);

        Size s = new Size (3,3);
        Imgproc.blur(image, image, s);
        Imgproc.Canny(image,image, 40, 100);

        int threshold = 50;
        int minLineSize = 20;
        int lineGap = 40;


        Imgproc.HoughLinesP(image, lines, 1, Math.PI / 180, threshold, minLineSize, lineGap);

        detectLines();

        convertIntoBitmap();



    }

    private void detectLines(){
        for (int x = 0; x < lines.rows(); x++){

           double[] vec = lines.get(x,0);
            x1 = vec[0];
            y1 = vec[1];
            x2 = vec[2];
            y2 = vec[3];

            startLine = new Point(x1, y1);
            endLine = new Point(x2, y2);


            // Hier wird Core.line also line nicht gefunden

            //Core.line(image, startLine, endLine, new Scalar(255,0,0), 3);
            Imgproc.line(imageWithLines,startLine,endLine,new Scalar(255,0,0), 5);
        }
    }





    private void convertIntoBitmap(){
        pBitmap = Bitmap.createBitmap(imageWithLines.cols(), imageWithLines.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(imageWithLines, pBitmap);
    }

    public Bitmap getImageBitmap(){

        return pBitmap;

    }
}
