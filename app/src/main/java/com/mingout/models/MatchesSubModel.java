package com.mingout.models;

/**
 * Created by Adnan Imtiaz on 10/29/2016.
 */

public class MatchesSubModel {
    private String title, imageUrl;

    public MatchesSubModel() {
    }

    public MatchesSubModel(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}