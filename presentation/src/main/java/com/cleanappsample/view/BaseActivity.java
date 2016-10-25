package com.cleanappsample.view;

import android.support.v7.app.AppCompatActivity;

import com.cleanappsample.CleanSampleApplication;
import com.cleanappsample.network.components.ApplicationComponent;

/**
 * Created by Anton Khorunzhyi on 10/10/2016.
 */

public class BaseActivity extends AppCompatActivity {

    protected ApplicationComponent getApplicationComponent(){
        return ((CleanSampleApplication)getApplication()).getApplicationComponent();
    }
}
