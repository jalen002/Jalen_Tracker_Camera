package com.ninjapiratestudios.trackercamera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class VideoFragment extends Fragment { // implements TextureView.SurfaceTextureListener{
    Camera camera;
    GLCamView glCamView;
    MediaRecorder mediaRecorder;
    Overlay overlay;
    boolean recordingActive;
    FrameLayout preview;

    //buttons
    ImageButton recordButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recordingActive = false;
        glCamView = new GLCamView(getActivity());
        overlay = new Overlay(getActivity());
        preview = new FrameLayout(getActivity());
        preview.addView(glCamView);

        LinearLayout buttonLL = new LinearLayout(getActivity());
        recordButton = new ImageButton(getActivity());
        recordButton.setImageResource(R.drawable.record);
        recordButton.setBackgroundColor(Color.TRANSPARENT);
        buttonLL.addView(recordButton);
        buttonLL.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        preview.addView(buttonLL);

        recordButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view){
                        recordToggle();
                    }
                }
        );

        return preview;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    private void captureCamera() {
        boolean camp = checkCameraHardware(getActivity());
        camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {

        }
    }

    private void recordToggle() {
        if(!recordingActive) {
            /*recordButton.setBackgroundColor(Color.RED);
            recordButton.setText("Stop");*/
            recordButton.setImageResource(R.drawable.stop);
            recordingActive = true;
            //preview.removeAllViews();
        }
        else{
            /*recordButton.setBackgroundColor(Color.GRAY);
            recordButton.setText("Record");*/
            recordButton.setImageResource(R.drawable.record);
            recordingActive = false;
        }
    }


//from google:
public static final int MEDIA_TYPE_IMAGE = 1;
public static final int MEDIA_TYPE_VIDEO = 2;

    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            //for now, show error eventually
            return null;
        }

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
}