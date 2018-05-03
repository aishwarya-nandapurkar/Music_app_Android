package com.example.dell.testapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dell.testapplication.data.ResultsObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This is our main class and the entry point. On this screen user will be shown
 * a field for entering the name of the artist and there will be a search button with which
 * user will be displayed a list of all the tracks related to that artist. Tapping on any item
 * of the list, he will be navigated to the next screen which will have the details of the tracks.
 */
public class MainActivity extends AppCompatActivity {

    private Button btnSearch;
    private EditText edtArtistName;
    //String to place our result in
    private String result;
    private ArrayList<ResultsObject> tracksList;
    private ListView artistTracksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = (Button) findViewById(R.id.search_button);
        edtArtistName = (EditText) findViewById(R.id.search_editText);
        artistTracksList = (ListView) findViewById(R.id.list_track_items);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtArtistName.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, R.string.searchedit_text_error, Toast.LENGTH_LONG).show();

                } else {
                    String artistName = edtArtistName.getText().toString();
                    artistName = artistName.replace(" ", "+");

                    String songReqUrl = "https://itunes.apple.com/search?term=" + artistName;

                    //Created a new instance of the class which is an asynctask
                    HTTPRequest getRequest = new HTTPRequest();

                    try {
                        result = (String) getRequest.execute(songReqUrl, "list", MainActivity.this).get();
                        tracksList = getResultObjectFromJson(result);

                        // This below method will actually display the list
                        loadTracks();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        Log.d("MainActivity", "Something went wrong in the try block" + e.getMessage());
                    }

                }
            }
        });
    }

    /**
     * This method will display the list of tracks by using the trackadapter which is a customised adapter that
     * we created for displaying the songs as a part of the list.
     */
    public void loadTracks() {
        if (tracksList != null && tracksList.size() > 0) {
            artistTracksList.setAdapter(new TrackAdapter(this, tracksList));
            artistTracksList.setVisibility(View.VISIBLE);

            artistTracksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent detailsScreenIntent = new Intent(MainActivity.this, TrackListActivity.class);
                    ResultsObject mRes = tracksList.get(position);
                    detailsScreenIntent.putExtra("track", mRes.getTrack());
                    detailsScreenIntent.putExtra("album", mRes.getCollectionName());
                    detailsScreenIntent.putExtra("genre", mRes.getGenre());
                    startActivity(detailsScreenIntent);
                }
            });
        }
    }

    /**
     * This method will be taking in the result as we get it from the API response and here it will
     * be parsed and the values will be inserted in an arraylist so that it can be used.
     *
     * @param result
     * @return
     */
    public ArrayList<ResultsObject> getResultObjectFromJson(String result) {

        ArrayList<ResultsObject> results = new ArrayList<ResultsObject>();
        String jsonStr = result;
        String track;
        String artistName;
        String collection;
        String genre;
        String imgArtwork;
        ResultsObject mResult;

        if (result != null && !jsonStr.equalsIgnoreCase("")) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray trackResults = jsonObj.getJSONArray("results");

                for (int i = 0; i < trackResults.length(); i++) {
                    JSONObject jResultObj = trackResults.getJSONObject(i);
                    track = (String) jResultObj.get("trackName");
                    artistName = (String) jResultObj.get("artistName");
                    collection = (String) jResultObj.get("collectionName");
                    genre = (String) jResultObj.get("primaryGenreName");
                    imgArtwork = (String) jResultObj.get("artworkUrl30");
                    mResult = new ResultsObject(track, artistName, collection, genre, imgArtwork);

                    results.add(mResult);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }
}
