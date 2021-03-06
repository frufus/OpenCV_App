package com.frufus.opencv20;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends Activity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;

    Button takePicture;
    ImageView imageView;
    SeekBar threshold;
    SeekBar minLineSize;
    SeekBar lineGap;
    File photoFile;
    String mCurrentPhotoPath;
    TransformImage transformImage;
    Bitmap lineBitmap;
    Button playButton;
    Bitmap imageBitmap;


    // debug
    private static String fileStorage = "File storage";
    static {
        if (!OpenCVLoader.initDebug()) {
            // Handle initialization error
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.resultImage);
        imageView.setImageBitmap(null);
        takePicture = (Button)findViewById(R.id.takePicture);
        takePicture.setOnClickListener(takePictureListener);
        threshold = (SeekBar)findViewById(R.id.threshold);
        minLineSize = (SeekBar)findViewById(R.id.minLineSize);
        lineGap = (SeekBar)findViewById(R.id.lineGap);
        threshold.setOnSeekBarChangeListener(seekBarsListener);
        minLineSize.setOnSeekBarChangeListener(seekBarsListener);
        lineGap.setOnSeekBarChangeListener(seekBarsListener);
        playButton = (Button)findViewById(R.id.playButton);
        playButton.setOnClickListener(playButtonListener);
    }

    SeekBar.OnSeekBarChangeListener seekBarsListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if(photoFile != null && transformImage != null){
                final Handler handler = new Handler();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        transformImage.drawLinesOnImage(photoFile, threshold.getProgress(), minLineSize.getProgress(), lineGap.getProgress());
                        imageBitmap = transformImage.getImageBitmap();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(imageBitmap);
                            }
                        });
                    }
                }).start();
            }
        }
    };

    View.OnClickListener takePictureListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    Log.d(fileStorage, ex.getMessage());
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile));
                    startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }
            }

        }
    };

    /**
     * recieving the image
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            final Handler handler = new Handler();
            transformImage = new TransformImage();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    transformImage.drawLinesOnImage(photoFile, threshold.getProgress(), minLineSize.getProgress(), lineGap.getProgress());
                    imageBitmap = transformImage.getImageBitmap();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(imageBitmap);
                        }
                    });
                }
            }).start();
        } else if (resultCode == RESULT_CANCELED) {
            // User cancelled the image capture
        } else {
            // Image capture failed, advise user
        }

    }

    private File createImageFile() throws IOException {
        Log.d(fileStorage, "State: " + Environment.getExternalStorageState());
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            Log.d(fileStorage, "storageDir: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = "file:" + image.getAbsolutePath();
            return image;
        }
        return null;
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            if (status == LoaderCallbackInterface.SUCCESS ) {
                // now we can call opencv code !

            } else {
                super.onManagerConnected(status);
            }
        }
    };
    @Override
    public void onResume() {;
        super.onResume();
        //OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9, this, mLoaderCallback);
        // you may be tempted, to do something here, but it's *async*, and may take some time,
        // so any opencv call here will lead to unresolved native errors.
    }
    View.OnClickListener playButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            BaseApp baseApp = (BaseApp) getApplicationContext();
            baseApp.setLines(transformImage.getLines());
            Intent playButtonIntent = new Intent (getApplicationContext(), PlayActivity.class);

            playButtonIntent.putExtra("File_Path", mCurrentPhotoPath);
            startActivity(playButtonIntent);
        }
    };
}
