package com.socratesdiaz.flickrbrowser.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.socratesdiaz.flickrbrowser.adapters.FlickrRecyclerViewAdapter;
import com.socratesdiaz.flickrbrowser.services.GetFlickrJsonData;
import com.socratesdiaz.flickrbrowser.R;
import com.socratesdiaz.flickrbrowser.adapters.RecyclerItemClickListener;
import com.socratesdiaz.flickrbrowser.models.Photo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private List<Photo> mPhotosList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private FlickrRecyclerViewAdapter flickrRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activateToolbar();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        flickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(MainActivity.this,
                new ArrayList<Photo>());
        mRecyclerView.setAdapter(flickrRecyclerViewAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Toast.makeText(MainActivity.this, "Long tap", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, ViewPhotoDetailsActivity.class);
                        intent.putExtra(PHOTO_TRANSFER, flickrRecyclerViewAdapter.getPhoto(position));
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                    }
                }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.menu_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String query = getSavedPreferenceData(FLICKR_QUERY);
        if(query.length() > 0) {
            ProccessPhotos proccessPhotos = new ProccessPhotos(query, true, this);
            proccessPhotos.execute();
        }

    }

    private String getSavedPreferenceData(String key) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getString(key, "");
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
                flickrRecyclerViewAdapter.loadNewData(getPhotos());
            }
        }
    }
}

