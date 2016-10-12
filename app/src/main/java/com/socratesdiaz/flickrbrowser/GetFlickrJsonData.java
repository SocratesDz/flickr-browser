package com.socratesdiaz.flickrbrowser;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by socratesdiaz on 10/6/16.
 */
public class GetFlickrJsonData extends GetRawData {

    private String LOG_TAG = GetFlickrJsonData.class.getSimpleName();
    private List<Photo> mPhotos;
    private Uri mDestinationUri;
    private Context mContext;


    public GetFlickrJsonData(String searchCriteria, boolean matchAll, Context context) {
        super(null);
        mContext = context;
        mPhotos = new ArrayList<>();
        createAndUpdateUri(searchCriteria, matchAll);
    }

    public void execute() {
        super.setmRawUrl(mDestinationUri.toString());
        DownloadJsonData downloadJsonData = new DownloadJsonData();
        Log.v(LOG_TAG, "Built URI = " + mDestinationUri.toString());
        downloadJsonData.execute(mDestinationUri.toString());
    }

    public boolean createAndUpdateUri(String searchCriteria, boolean matchAll) {
        //Uri.parse();
        final String FLICKR_API_BASE_URL = mContext.getString(R.string.base_url);
        final String TAGS_PARAM = mContext.getString(R.string.param_tags);
        final String TAGMODE_PARAM = mContext.getString(R.string.param_tagmode);
        final String FORMAT_PARAM = mContext.getString(R.string.param_format);
        final String NO_JSON_CALLBACK_PARAM = mContext.getString(R.string.param_nojsoncallback);

        mDestinationUri = Uri.parse(FLICKR_API_BASE_URL).buildUpon()
                .appendQueryParameter(TAGS_PARAM, searchCriteria)
                .appendQueryParameter(TAGMODE_PARAM, matchAll ? "ALL" : "ANY")
                .appendQueryParameter(FORMAT_PARAM, "json")
                .appendQueryParameter(NO_JSON_CALLBACK_PARAM, "1")
                .build();

        return mDestinationUri != null;
    }

    public List<Photo> getPhotos() {
        return mPhotos;
    }

    public void processResult() {
        if(getmDownloadStatus() != DownloadStatus.OK) {
            Log.e(LOG_TAG, "Error downloading raw file");
            return;
        }

        final String FLICKR_ITEMS = mContext.getString(R.string.res_items);
        final String FLICKR_TITLE = mContext.getString(R.string.res_title);
        final String FLICKR_MEDIA = mContext.getString(R.string.res_media);
        final String FLICKR_PHOTO_URL = mContext.getString(R.string.res_photo_url);
        final String FLICKR_AUTHOR = mContext.getString(R.string.res_author);
        final String FLICKR_AUTHOR_ID = mContext.getString(R.string.res_author_id);
        final String FLICKR_LINK = mContext.getString(R.string.res_link);
        final String FLICKR_TAGS = mContext.getString(R.string.res_tags);

        try {
            JSONObject jsonData = new JSONObject(getmData());
            JSONArray itemsArray = jsonData.getJSONArray(FLICKR_ITEMS);
            for(int i = 0; i < itemsArray.length(); i++) {

                JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                String title = jsonPhoto.getString(FLICKR_TITLE);
                String author = jsonPhoto.getString(FLICKR_AUTHOR);
                String authorId = jsonPhoto.getString(FLICKR_AUTHOR_ID);
                //String link = jsonPhoto.getString(FLICKR_LINK);
                String tags = jsonPhoto.getString(FLICKR_TAGS);

                JSONObject jsonMedia = jsonPhoto.getJSONObject(FLICKR_MEDIA);
                String photoUrl = jsonMedia.getString(FLICKR_PHOTO_URL);
                String link = photoUrl.replaceFirst("_m.", "_b.");

                Photo photoObject = new Photo(title, author, authorId, link, tags, photoUrl);

                this.mPhotos.add(photoObject);

            }

            for(Photo singlePhoto: mPhotos) {
                Log.v(LOG_TAG, singlePhoto.toString());
            }

        } catch (JSONException jsone) {
            jsone.printStackTrace();
            Log.e(LOG_TAG, "Error processing Json data");
        }
    }

    public class DownloadJsonData extends DownloadRawData {
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            processResult();
        }

        protected String doInBackground(String... params) {
            String[] par = { mDestinationUri.toString() };
            return super.doInBackground(par);
        }


    }
}
