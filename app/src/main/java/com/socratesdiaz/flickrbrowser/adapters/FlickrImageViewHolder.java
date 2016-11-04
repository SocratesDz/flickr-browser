package com.socratesdiaz.flickrbrowser.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.socratesdiaz.flickrbrowser.R;

/**
 * Created by socratesdiaz on 10/7/16.
 */
public class FlickrImageViewHolder extends RecyclerView.ViewHolder {
    protected ImageView thumbnail;
    protected TextView title;

    public FlickrImageViewHolder(View itemView) {
        super(itemView);
        this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
    }
}
