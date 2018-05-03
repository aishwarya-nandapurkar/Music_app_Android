package com.example.dell.testapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.testapplication.data.ResultsObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This is a custom adapter class used for creating a view for all the elements in the list.
 */

public class TrackAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ResultsObject> trackResults;
    private static LayoutInflater layoutInflater = null;

    public TrackAdapter(@NonNull Context context, @NonNull List<ResultsObject> objects) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        trackResults = (ArrayList<ResultsObject>) objects;
    }


    @Nullable
    @Override
    public ResultsObject getItem(int position) {
        return trackResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return trackResults.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        // Checking if the earlier view exists otherwise creating it.
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_list_cell, parent, false);
            holder = new ViewHolder();
            holder.trackTxtView = (TextView) convertView.findViewById(R.id.txt_title);
            holder.durationTxtView = (TextView) convertView.findViewById(R.id.txt_duration);
            holder.trackImgView = (ImageView) convertView.findViewById(R.id.img_track);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Fetching the objects from the arraylist and assigning the values to the list's elements
        ResultsObject resObj = (ResultsObject) trackResults.get(position);
        holder.trackTxtView.setText(resObj.getTrack());

        String imgUrl = resObj.getImgArtwork();
        Bitmap imgBmp = null;
        try {
            imgBmp = new ImageDownloaderTask().execute(imgUrl).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TrackAdapter", "Failed while fetching images with exception :" + e.getMessage());
        }

        if (imgBmp != null) {
            holder.trackImgView.setImageBitmap(imgBmp);
        } else {
            holder.trackImgView.setImageDrawable(context.getResources().getDrawable(R.drawable.default_music));
        }

        return convertView;

    }

    static class ViewHolder {
        TextView trackTxtView;
        TextView durationTxtView;
        ImageView trackImgView;
    }

    private Bitmap getBitmapFromURL(String imgUrl) {
        Bitmap jpg = null;

        if (imgUrl != null && !imgUrl.equalsIgnoreCase("")) {
            HTTPRequest getImgRequest = new HTTPRequest();
            try {
                InputStream imgStream = (InputStream) getImgRequest.execute(imgUrl, "bitmap").get();
                if (imgStream != null) {
                    jpg = BitmapFactory.decodeStream(imgStream);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("TrackAdapter", "Failed while fetching images with exception :" + e.getMessage());
            }
        }
        return jpg;
    }

}
