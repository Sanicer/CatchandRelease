package com.sanics.catchandrelease;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.R.attr.data;

/**
 * Created by Sanic on 3/10/2016.
 */

public class ScoreAdapter extends ArrayAdapter<Score> {
    private Context context;
    private int layoutResourceId;
    //private Score mScores[] = null;
    private ArrayList<Score> mScores;

    public ScoreAdapter(Context context, int layoutResourceId, ArrayList<Score> mScores) {
        super(context, layoutResourceId, mScores);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.mScores = mScores;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        //ScoreHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            //holder = new ScoreHolder();
            //holder.name = (TextView)row.findViewById(R.id.rowTextView);
            //holder.score = (TextView)row.findViewById(R.id.rowTextView2);

            //row.setTag(holder);
        }// else {
         //   holder = (ScoreHolder)row.getTag();
        //}

        Score mScore = mScores.get(position);
        if (mScore != null) {
            TextView name = (TextView)row.findViewById(R.id.rowTextView);
            TextView highScore = (TextView)row.findViewById(R.id.rowTextView2);

            if (name != null) {
                name.setText(mScore.getmName());
            }
            if (highScore != null) {
                //Log.d("HighScore", "HighScore = " + mScore.getmScore());
                //Log.d("HighScore", "HighScore = " + Integer.toString(mScore.getmScore()));
                highScore.setText(Integer.toString(mScore.getmScore()));
            }
        }

        /*Score score = mScores[position];
        holder.name.setText(score.getmName());
        holder.score.setText(Integer.toString(score.getmScore())); */

        return row;
    }

    /*static class ScoreHolder {
        TextView name;
        TextView score;
    }*/
}
