package com.example.dell.testapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.dell.testapplication.data.ResultsObject;

import java.util.ArrayList;

/*
 * This class will display the details of the track selected by the user.
 * We can add more elements in this class depending upon our requirement.
 */
public class TrackListActivity extends AppCompatActivity {
    private TextView txtAlbumName, txtGenreName, txtTrackName;
    private ArrayList<ResultsObject> tracks;
    private String album, genre, track;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_track_list_activity);
        /**
         * As all the values here are coming from the selection made on the previous screen,
         * we will be getting the values from the earlier screen in the intent.
         */
        txtAlbumName = (TextView) findViewById(R.id.txt_album_name);
        txtGenreName = (TextView) findViewById(R.id.txt_collection_name);
        txtTrackName = (TextView) findViewById(R.id.song_title);

        Intent values = getIntent();
        if (values != null) {
            album = values.getStringExtra("album");
            genre = values.getStringExtra("genre");
            track = values.getStringExtra("track");

            txtAlbumName.setText(album);
            txtGenreName.setText(genre);
            txtTrackName.setText(track);
        }
    }

}
