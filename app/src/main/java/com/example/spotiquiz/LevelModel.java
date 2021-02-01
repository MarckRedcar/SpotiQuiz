package com.example.spotiquiz;

import android.widget.ImageView;

public class LevelModel {

    private ImageView levelAvatar;
    private String levelScore;

    public LevelModel() {
        this.levelAvatar = levelAvatar;
        this.levelScore = levelScore;
    }

    public ImageView getLevelAvatar() { return levelAvatar; }

    public void setLevelAvatar(ImageView levelAvatar) { this.levelAvatar = levelAvatar; }

    public String getLevelScore() { return levelScore; }

    public void setLevelScore(String levelScore) { this.levelScore = levelScore; }
}
