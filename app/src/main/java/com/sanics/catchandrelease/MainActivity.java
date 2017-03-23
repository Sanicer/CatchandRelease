package com.sanics.catchandrelease;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // test code for scrabble begin:
//    private int[] lettersCaught = {5,6,7,8,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};
//    private static final String EXTRA_GAMEACTIVITY = "com.example.EXTRA_GAMEACTIVITY";
    // test code for scrabble end

    public void startGame(View v) {
        // prod code start:
        Intent myIntent = new Intent(this, GameActivity.class);
        startActivity(myIntent);
        // prod code end

        // test code for scrabble begin:
//        Intent i = new Intent(this, ScrabbleActivity.class);
//        i.putExtra(EXTRA_GAMEACTIVITY, lettersCaught);
//        startActivity(i);
        // test code for scrabble end
    }

    public void highScore (View v) {
        Intent myIntent = new Intent(this, HighScoreActivity.class);
        startActivity(myIntent);
    }

    public void instructions (View v) {
        Intent myIntent = new Intent(this, HelpActivity.class);
        startActivity(myIntent);
    }
}
