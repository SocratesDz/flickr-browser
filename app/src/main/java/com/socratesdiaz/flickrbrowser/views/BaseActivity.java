package com.socratesdiaz.flickrbrowser.views;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.socratesdiaz.flickrbrowser.R;

/**
 * Created by socratesdiaz on 10/10/16.
 */
public class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    public static final String FLICKR_QUERY = "FLICKR_QUERY";
    public static final String PHOTO_TRANSFER = "PHOTO_TRANSFER";

    protected Toolbar activateToolbar() {
        if(mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.app_bar);
            if(mToolbar != null) {
                setSupportActionBar(mToolbar);
            }
        }
        return mToolbar;
    }

    protected Toolbar activateToolbarWithHomeEnabled() {
        activateToolbar();
        if(mToolbar != null) {
            if(getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return mToolbar;
    }
}
