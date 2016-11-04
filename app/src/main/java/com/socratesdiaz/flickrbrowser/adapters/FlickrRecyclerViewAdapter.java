package com.socratesdiaz.flickrbrowser.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.socratesdiaz.flickrbrowser.R;
import com.socratesdiaz.flickrbrowser.models.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by socratesdiaz on 10/7/16.
 */
public class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrImageViewHolder> {
    private List<Photo> mPhotosList;
    private Context mContext;
    private static final String LOG_TAG = FlickrRecyclerViewAdapter.class.getSimpleName();

    public FlickrRecyclerViewAdapter(Context context, List<Photo> photosList) {
        this.mContext = context;
        this.mPhotosList = photosList;
    }

    @Override
    public FlickrImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, null);
        FlickrImageViewHolder flickrImageViewHolder = new FlickrImageViewHolder(view);
        return flickrImageViewHolder;
    }

    @Override
    public void onBindViewHolder(FlickrImageViewHolder holder, int position) {
        Photo photoItem = mPhotosList.get(position);
        Log.d(LOG_TAG, "Processing: " + photoItem.getTitle() + " --> " + Integer.toString(position));

        Picasso.with(mContext).load(photoItem.getImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return (null != mPhotosList ? mPhotosList.size() : 0);
    }

    public void loadNewData(List<Photo> newPhotos) {
        mPhotosList = newPhotos;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int position) {
        return (null != mPhotosList ? mPhotosList.get(position) : null);
    }
}
