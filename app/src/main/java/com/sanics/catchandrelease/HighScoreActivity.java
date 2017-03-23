package com.sanics.catchandrelease;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class HighScoreActivity extends AppCompatActivity {
    private static final String EXTRA_SCRABBLEACTIVITYSCORE = "com.example.EXTRA_SCRABBLEACTIVITYSCORE";
    private static final String SAVE_TAG = "SAVE_TAG";

    private ListView mainListView;
    private ArrayAdapter<Score> listAdapter;

    private int mCurrentScore = 0;
    private String mCurrentName;
    private ArrayList<Score> mScores;
    private Score mScore;
    private Score mNewScore;
    private boolean anonTrue = false;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        // Retrieve current score from intent and ask for name
        extras = getIntent().getExtras();
        if (extras != null) {
            mCurrentScore = extras.getInt(EXTRA_SCRABBLEACTIVITYSCORE);

            // Ask for name
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter name");

            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mCurrentName = input.getText().toString();
                    compareScores();
                    setAdapter();
                }
            });
            builder.setNegativeButton("Anonymous", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mCurrentName = "";
                    anonTrue = true;
                    dialog.cancel();

                    compareScores();
                    setAdapter();
                }
            });

            builder.show();
        } else {
            compareScores();
            setAdapter();
        }

        Button clearScoresBtn = (Button) findViewById(R.id.clearScoresBtn);
        clearScoresBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedprefs = getSharedPreferences("CatchAndReleaseHighScore", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedprefs.edit();
                editor.remove("SAVE_TAG");
                editor.commit();

                mScores = new ArrayList<>();
                setAdapter();
            }
        });

}

    private void compareScores() {
        mScores = new ArrayList<>();
        // Retrieve old high score
        SharedPreferences sharedprefs = this.getSharedPreferences("CatchAndReleaseHighScore", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedprefs.getString(SAVE_TAG, null);
        Type type = new TypeToken<ArrayList<Score>>() {}.getType();
        mScores = gson.fromJson(json, type);

        // First high score
        if (mScores == null) {
            mScores = new ArrayList<>();
            if (extras != null) {
                mNewScore = new Score(mCurrentName, mCurrentScore);
                mScores.add(mNewScore);

                // Save new high score
                SharedPreferences.Editor editor = sharedprefs.edit();
                json = gson.toJson(mScores);
                editor.putString(SAVE_TAG, json);
                editor.commit();
            }
        } else { // Check if current score is higher than any of the saved scores
            for (int i = 0; i < mScores.size(); i++) {
                mScore = mScores.get(i);
                if (mCurrentScore > mScore.getmScore()) {
                    mNewScore = new Score(mCurrentName, mCurrentScore);

                    if (mScores.size() > 5) {
                        mScores.remove(i);
                    }
                    mScores.add(i, mNewScore);

                    // Save new high score
                    SharedPreferences.Editor editor = sharedprefs.edit();
                    json = gson.toJson(mScores);
                    editor.putString(SAVE_TAG,json);
                    editor.commit();
                    break;
                }
            }
        }
    }

    private void setAdapter() {
        ScoreAdapter adapter = new ScoreAdapter(this, R.layout.high_score_listview_text, mScores);

        mainListView = (ListView)findViewById(R.id.highScoreListView);
        mainListView.setAdapter(adapter);

    }
}
