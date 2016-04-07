package com.ninjapiratestudios.trackercamera;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageButton;

import java.io.File;
import java.util.Locale;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class VideoActivity extends FragmentActivity implements
        ItemFragment.OnListFragmentInteractionListener, VideoFragment.OnVideoAddedListener{
    public final static String LOG_TAG = "VIDEO_ACTIVITY";
    private ViewPager mViewPager;
    private boolean intentAppExit; // Prevents release of camera resources
    private ImageButton recordButton;
    private boolean stopImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        // Setup PagerAdapter for swiping functionality
        PagerAdapter pagerAdapter;
        mViewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
    }

    /**
     * Checks to see if the app has been exited due to an Intent to the Gallery.
     *
     * @return Whether or not the above condition is true.
     */
    public boolean isIntentAppExit() {
        return intentAppExit;
    }

    public void setIntentAppExit(boolean intentAppExit) {
        this.intentAppExit = intentAppExit;
    }

    /**
     * A simple pager adapter that represents 2 Fragment objects, in
     * sequence.
     */
    protected class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }
        private VideoFragment vF;
        private ItemFragment iF;
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                if(vF == null) {
                    vF = new VideoFragment();
                    return vF;
                } else {
                    return vF;
                }
            } else {
                if(iF == null)
                {
                     iF = new ItemFragment();
                    return iF;
                } else {
                    return iF;
                }
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.record_page).toUpperCase(l);
                case 1:
                    return getString(R.string.sharing).toUpperCase(l);
            }
            return null;
        }
    }

    @Override
    public void onListFragmentInteraction(File f) {
        intentAppExit = true;
        Uri videoUri = Uri.fromFile(f);
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_VIEW);
        galleryIntent.setDataAndType(videoUri, "video/*");
        galleryIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(galleryIntent);
    }

    @Override
    public void onVideoAdded() {
        Log.i("VideoAdded called", "the OnVideoAdded Callback was called");
        ScreenSlidePagerAdapter sSPA = (ScreenSlidePagerAdapter) mViewPager.getAdapter();
        ItemFragment iF = (ItemFragment) sSPA.getItem(1);
        iF.update();
    }

    /**
     * Getter for the Record Button.
     * @return a reference to the Record Button.
     */
    public ImageButton getRecordButton() {
        return recordButton;
    }

    /**
     * Setter for the new Record Button.
     * @param recordButton The new Record Button object.
     */
    public void setRecordButton(ImageButton recordButton) {
        this.recordButton = recordButton;
    }

    /**
     * Toggles setting the stop.png and record.png image types for<br/>
     * the Record button
     */
    public void toggleRecordButton() {
        if(recordButton != null) {
            if(!stopImage) {
                stopImage = true;
                recordButton.setImageResource(R.drawable.stop);
            } else {
                stopImage = false;
                recordButton.setImageResource(R.drawable.record);
            }
        }
    }
}