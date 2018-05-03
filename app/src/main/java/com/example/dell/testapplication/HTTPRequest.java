package com.example.dell.testapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPRequest extends AsyncTask {
    /*
     * Declaring variables for the Request that we will be sending in.
     *
     */
    public static final String ReqType = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] params) {

        String stringUrl = (String) params[0];
        String typeOfResult = (String) params[1];
        String result;
        String inputLine;
        HttpURLConnection connection = null;


        try {
            // This will be the url which will be requested by use
            URL myUrl = new URL(stringUrl);

            connection = (HttpURLConnection)
                    myUrl.openConnection();
            //Assigning the properties to the connection
            connection.setRequestMethod(ReqType);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            //Connect to our url
            connection.connect();

            InputStream inputStream = connection.getInputStream();

            //Creating a InputStreamReader object to fetch the input stream from the requested URL
            InputStreamReader streamReader = new
                    InputStreamReader(inputStream);

            BufferedReader reader = new BufferedReader(streamReader);

            StringBuilder stringBuilder = new StringBuilder();

            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            //Closing the resources
            reader.close();
            streamReader.close();

            if (typeOfResult.equalsIgnoreCase("bitmap")) {
                return inputStream;
            }

            result = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            result = null;
        } finally {
            // Closing the connection
            if (connection != null) {
                connection.disconnect();
            }
        }

        return result;
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
