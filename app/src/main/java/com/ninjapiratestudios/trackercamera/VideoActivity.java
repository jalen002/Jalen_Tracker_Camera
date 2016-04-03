package com.ninjapiratestudios.trackercamera;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class VideoActivity extends FragmentActivity implements
        ItemFragment.OnListFragmentInteractionListener {
    public final static String LOG_TAG = "VIDEO_ACTIVITY";
    private ViewPager mViewPager;
    private int PAGE_NUM = 2;
    private boolean intentAppExit; // Prevents release of camera resources

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        // Setup PagerAdapter for swiping functionality
        PagerAdapter pagerAdapter = null;
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

    /**
     * A simple pager adapter that represents 2 Fragment objects, in
     * sequence.
     */
    protected class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                VideoFragment vf = new VideoFragment();
                return vf;
            } else {
                ItemFragment iF = new ItemFragment();
                return iF;
            }
        }

        @Override
        public int getCount() {
            return PAGE_NUM;
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
}