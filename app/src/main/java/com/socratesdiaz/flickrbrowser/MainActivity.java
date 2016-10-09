package com.socratesdiaz.flickrbrowser;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private List<Photo> mPhotosList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private FlickrRecyclerViewAdapter flickrRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProccessPhotos proccessPhotos = new ProccessPhotos("google", true, this);
        proccessPhotos.execute();
    }

    public class ProccessPhotos extends GetFlickrJsonData {
        public ProccessPhotos(String searchCriteria, boolean matchAll, Context context) {
            super(searchCriteria, matchAll, context);
        }

        public void execute() {
            super.execute();
            ProcessData processData = new ProcessData();
            processData.execute();
        }

        public class ProcessData extends DownloadJsonData {
            protected void onPostExecute(String webData) {
                super.onPostExecute(webData);
                flickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(MainActivity.this, getPhotos());
                mRecyclerView.setAdapter(flickrRecyclerViewAdapter);
            }
        }
    }
}

