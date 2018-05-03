package com.example.dell.testapplication.data;

import java.io.Serializable;
import java.util.HashMap;


/**
 * This is POJO class which is just used for the result retrieved from the iTunes API that we are calling.
 * It has all the elements that we will be requiring from the JSON response we will be getting from the
 * API. Getter, setters are used so that the values can be retrieved and set easily.
 */
public class ResultsObject implements Serializable {

    private String track;
    private String artistName;
    private String genre;
    private String imgArtwork;
    private String collectionName;
    public static HashMap<String, Object> mDataCache = new HashMap<String, Object>();

    public ResultsObject(String track, String artistName, String collectionName, String genre, String imgArtwork) {
        this.track = track;
        this.artistName = artistName;
        this.collectionName = collectionName;
        this.genre = genre;
        this.imgArtwork = imgArtwork;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImgArtwork() {
        return imgArtwork;
    }

    public void setImgArtwork(String imgArtwork) {
        this.imgArtwork = imgArtwork;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }


}
