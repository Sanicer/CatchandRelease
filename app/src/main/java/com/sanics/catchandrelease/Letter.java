package com.sanics.catchandrelease;

import android.widget.ImageView;

/**
 * Created by Sanic on 8/08/2016.
 */
public class Letter {
    private ImageView mLetterImage;
    private int mLetterNum;
    private int speed;
    private boolean hit = false;
    private boolean isGold;

    public Letter (ImageView letterImg, int letterNum, int letterSpeed, boolean gold) {
        this.mLetterImage = letterImg;
        this.mLetterNum = letterNum;
        this.speed = letterSpeed;
        this.isGold = gold;
    }

    public ImageView getmLetterImage() {
        return mLetterImage;
    }

    public int getmLetterNum() {
        return mLetterNum;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isGold() {
        return isGold;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
