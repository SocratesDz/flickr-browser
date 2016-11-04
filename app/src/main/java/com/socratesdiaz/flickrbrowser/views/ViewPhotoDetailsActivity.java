package com.socratesdiaz.flickrbrowser.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.socratesdiaz.flickrbrowser.R;
import com.socratesdiaz.flickrbrowser.models.Photo;
import com.squareup.picasso.Picasso;

public class ViewPhotoDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_details);
        activateToolbarWithHomeEnabled();

        Intent intent = getIntent();
        Photo photo = (Photo) intent.getSerializableExtra(PHOTO_TRANSFER);

        TextView photoTitle = (TextView) findViewById(R.id.photo_title);
        photoTitle.setText("Title: " + photo.getTitle());

        TextView photoTags = (TextView) findViewById(R.id.photo_tags);
        photoTags.setText("Tags: " + photo.getTags());

        TextView photoAuthor = (TextView) findViewById(R.id.photo_author);
        photoAuthor.setText("Author: " + photo.getAuthor());

        ImageView photoImage = (ImageView) findViewById(R.id.photo_image);
        Picasso.with(this).load(photo.getLink())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(photoImage);
    }
}
