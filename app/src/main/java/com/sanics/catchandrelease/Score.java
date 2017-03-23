package com.sanics.catchandrelease;

/**
 * Created by Sanic on 3/10/2016.
 */

public class Score {
    private String mName;
    private int mScore;

    public Score(String Name, int Score) {
        this.mName = Name;
        this.mScore = Score;
    }

    public String getmName() {
        return mName;
    }

    public int getmScore() {
        return mScore;
    }
}
