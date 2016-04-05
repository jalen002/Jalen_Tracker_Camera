package com.ninjapiratestudios.trackercamera;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class VideoFragment extends Fragment {
    public final static String LOG_TAG = "VIDEO_FRAGMENT";
    boolean recordingActive;
    private CameraRecorder cameraRecorder;
    private VideoActivity activity;
    private OnVideoAddedListener vAListener;

    //buttons
    ImageButton recordButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        vAListener = (OnVideoAddedListener) context;
        activity = (VideoActivity)getActivity();
        // Prepare camera and OpenGL
        initializeCameraRecorder();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FrameLayout preview = new FrameLayout(getActivity());
        LinearLayout buttonLL = new LinearLayout(getActivity());

        // Add CameraPreview and Record Button to FrameLayout
        cameraRecorder.cameraPreviewSetup(preview);
        recordButton = new ImageButton(getActivity());
        recordButton.setImageResource(R.drawable.record);
        recordButton.setBackgroundColor(Color.TRANSPARENT);
        buttonLL.addView(recordButton);
        buttonLL.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        preview.addView(buttonLL);

        recordButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        recordToggle();
                    }
                }
        );

        Log.i(LOG_TAG, "VideoFragment layout constructed.");
        return preview;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(activity.isIntentAppExit()) {
            // Allow resources to be released after Gallery app exits
            activity.setIntentAppExit(false);
            Log.i(LOG_TAG, "Reset intentAppExit.");
        }
    }

    /**
     * Releases the Camera resource for other apps.
     */
    @Override
    public void onPause() {
        super.onPause();
        if(!activity.isIntentAppExit()) {
            cameraRecorder.releaseMediaResource();
            cameraRecorder.releaseCameraResource();
        }
        Log.i(LOG_TAG, "onPause()");
    }

    /**
     * Initializes the CameraRecorder class that is necessary for video
     * recording and OpenGL functionality.
     */
    private void initializeCameraRecorder() {
            cameraRecorder = CameraRecorder.newInstance((VideoActivity)
                    getActivity());
    }

    private void recordToggle() {
        if (!recordingActive) {
            /*recordButton.setBackgroundColor(Color.RED);
            recordButton.setText("Stop");*/
            recordButton.setImageResource(R.drawable.stop);
            recordingActive = true;
            cameraRecorder.displayFileNameDialog();
            //preview.removeAllViews();
        } else {
            /*recordButton.setBackgroundColor(Color.GRAY);
            recordButton.setText("Record");*/
            recordButton.setImageResource(R.drawable.record);
            recordingActive = false;
            vAListener.onVideoAdded();

        }
    }

    /**
     * A listener that will be used to communicate with the activity when a new video is recorded.
     */
    public interface OnVideoAddedListener
    {
        void onVideoAdded();
    }
}