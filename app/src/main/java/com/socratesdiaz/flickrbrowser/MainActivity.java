package com.socratesdiaz.flickrbrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetRawData rawData = new GetRawData(getString(R.string.url));
        rawData.execute();
    }
}
