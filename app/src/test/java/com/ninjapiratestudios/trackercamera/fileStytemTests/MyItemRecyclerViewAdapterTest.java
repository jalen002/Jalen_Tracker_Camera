package com.ninjapiratestudios.trackercamera.fileStytemTests;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ninjapiratestudios.trackercamera.ItemFragment;
import com.ninjapiratestudios.trackercamera.MyItemRecyclerViewAdapter;
import com.ninjapiratestudios.trackercamera.fileContent.FileContent;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import static org.mockito.Mockito.times;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.mockito.BDDMockito.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;

/**
 * Tests The recycler View Adapter Class
 *
 * @author Benjmain Boudra
 * @version 1.0
 * @date 3/20/2016
 */
public class MyItemRecyclerViewAdapterTest extends BaseTest{

    MyItemRecyclerViewAdapter mIRVA;
    @Before
    public void setup()
    {
        ItemFragment.OnListFragmentInteractionListener oLFIL = Mockito.mock(ItemFragment.OnListFragmentInteractionListener.class);
        File[] files = super.generateExternalStorageFileMockObjects();
        FileContent fC = super.generateFileContentLength8(files);
        mIRVA = new MyItemRecyclerViewAdapter(fC.getItems(),oLFIL);
    }


    @Test
    public void getCountShouldReturn8()
    {
        //given

        //when

        //then
        assertEquals(8, mIRVA.getItemCount());
    }

    @Test
    public void onCreateViewShouldcallRecycler()
    {

    }


}
