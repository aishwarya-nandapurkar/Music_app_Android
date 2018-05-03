package com.example.dell.testapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class is used for downloading the bitmaps for the tracks.
 * Using an async task here.
 */
class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... params) {
        return fetchBitmap(params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

    }

    /**
     * This method will take url as the param and download the bitmap from this url.
     *
     * @param url
     * @return Bitmap of the track
     */
    private Bitmap fetchBitmap(String url) {
        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            int statusCode = urlConnection.getResponseCode();
            // Trying to make an HTTP connection and if the status is not OK then returning from the function.
            if (statusCode != 200) {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            urlConnection.disconnect();
            Log.d("ImageDownloader", "Error downloading image from " + url);
        } finally {
            // Closing the connection every single time.
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }
}

