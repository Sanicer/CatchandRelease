package com.sanics.catchandrelease;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ScrabbleActivity extends AppCompatActivity {
    private static final String EXTRA_GAMEACTIVITY = "com.example.EXTRA_GAMEACTIVITY";
    private static final String EXTRA_GAMEACTIVITYSCORE = "com.example.EXTRA_GAMEACTIVITYSCORE";
    private static final String EXTRA_SCRABBLEACTIVITYSCORE = "com.example.EXTRA_SCRABBLEACTIVITYSCORE";

    private int[] lettersCaught;
    private TextView letterANumber, letterBNumber, letterCNumber, letterDNumber, letterENumber,
            letterFNumber, letterGNumber, letterHNumber, letterINumber, letterJNumber,
            letterKNumber, letterLNumber, letterMNumber, letterNNumber, letterONumber,
            letterPNumber, letterQNumber, letterRNumber, letterSNumber, letterTNumber,
            letterUNumber, letterVNumber, letterWNumber, letterXNumber, letterYNumber, letterZNumber;
    private ImageView letterAImage, letterBImage, letterCImage, letterDImage, letterEImage,
            letterFImage, letterGImage, letterHImage, letterIImage, letterJImage,
            letterKImage, letterLImage, letterMImage, letterNImage, letterOImage,
            letterPImage, letterQImage, letterRImage, letterSImage, letterTImage,
            letterUImage, letterVImage, letterWImage, letterXImage, letterYImage, letterZImage;
    private TextView mTextViewWord;
    private String mWord;
    private Button deleteButton, clearAllButton, submitButton;
    private int mScore;
    private TextView mScoreTextView;
    private CountDownTimer myCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrabble);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setTimer();
        Bundle extras = getIntent().getExtras();

        mScore = extras.getInt(EXTRA_GAMEACTIVITYSCORE);
        mScoreTextView = (TextView) findViewById(R.id.score);
        mScoreTextView.setText("Score: " + String.valueOf(mScore));

        lettersCaught = extras.getIntArray(EXTRA_GAMEACTIVITY);

        letterANumber = (TextView) findViewById(R.id.letter_a_count);
        letterANumber.setText(String.valueOf(lettersCaught[0]));

        letterBNumber = (TextView) findViewById(R.id.letter_b_count);
        letterBNumber.setText(String.valueOf(lettersCaught[1]));

        letterCNumber = (TextView) findViewById(R.id.letter_c_count);
        letterCNumber.setText(String.valueOf(lettersCaught[2]));

        letterDNumber = (TextView) findViewById(R.id.letter_d_count);
        letterDNumber.setText(String.valueOf(lettersCaught[3]));

        letterENumber = (TextView) findViewById(R.id.letter_e_count);
        letterENumber.setText(String.valueOf(lettersCaught[4]));

        letterFNumber = (TextView) findViewById(R.id.letter_f_count);
        letterFNumber.setText(String.valueOf(lettersCaught[5]));

        letterGNumber = (TextView) findViewById(R.id.letter_g_count);
        letterGNumber.setText(String.valueOf(lettersCaught[6]));

        letterHNumber = (TextView) findViewById(R.id.letter_h_count);
        letterHNumber.setText(String.valueOf(lettersCaught[7]));

        letterINumber = (TextView) findViewById(R.id.letter_i_count);
        letterINumber.setText(String.valueOf(lettersCaught[8]));

        letterJNumber = (TextView) findViewById(R.id.letter_j_count);
        letterJNumber.setText(String.valueOf(lettersCaught[9]));

        letterKNumber = (TextView) findViewById(R.id.letter_k_count);
        letterKNumber.setText(String.valueOf(lettersCaught[10]));

        letterLNumber = (TextView) findViewById(R.id.letter_l_count);
        letterLNumber.setText(String.valueOf(lettersCaught[11]));

        letterMNumber = (TextView) findViewById(R.id.letter_m_count);
        letterMNumber.setText(String.valueOf(lettersCaught[12]));

        letterNNumber = (TextView) findViewById(R.id.letter_n_count);
        letterNNumber.setText(String.valueOf(lettersCaught[13]));

        letterONumber = (TextView) findViewById(R.id.letter_o_count);
        letterONumber.setText(String.valueOf(lettersCaught[14]));

        letterPNumber = (TextView) findViewById(R.id.letter_p_count);
        letterPNumber.setText(String.valueOf(lettersCaught[15]));

        letterQNumber = (TextView) findViewById(R.id.letter_q_count);
        letterQNumber.setText(String.valueOf(lettersCaught[16]));

        letterRNumber = (TextView) findViewById(R.id.letter_r_count);
        letterRNumber.setText(String.valueOf(lettersCaught[17]));

        letterSNumber = (TextView) findViewById(R.id.letter_s_count);
        letterSNumber.setText(String.valueOf(lettersCaught[18]));

        letterTNumber = (TextView) findViewById(R.id.letter_t_count);
        letterTNumber.setText(String.valueOf(lettersCaught[19]));

        letterUNumber = (TextView) findViewById(R.id.letter_u_count);
        letterUNumber.setText(String.valueOf(lettersCaught[20]));

        letterVNumber = (TextView) findViewById(R.id.letter_v_count);
        letterVNumber.setText(String.valueOf(lettersCaught[21]));

        letterWNumber = (TextView) findViewById(R.id.letter_w_count);
        letterWNumber.setText(String.valueOf(lettersCaught[22]));

        letterXNumber = (TextView) findViewById(R.id.letter_x_count);
        letterXNumber.setText(String.valueOf(lettersCaught[23]));

        letterYNumber = (TextView) findViewById(R.id.letter_y_count);
        letterYNumber.setText(String.valueOf(lettersCaught[24]));

        letterZNumber = (TextView) findViewById(R.id.letter_z_count);
        letterZNumber.setText(String.valueOf(lettersCaught[25]));

        mTextViewWord = (TextView) findViewById(R.id.textView);
        mWord = "";

        letterAImage = (ImageView) findViewById(R.id.letter_a);
        letterAImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[0] > 0) {
                            lettersCaught[0]--;
                            letterANumber.setText(String.valueOf(lettersCaught[0]));

                            mWord = mWord + "a";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No As to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterBImage = (ImageView) findViewById(R.id.letter_b);
        letterBImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[1] > 0) {
                            lettersCaught[1]--;
                            letterBNumber.setText(String.valueOf(lettersCaught[1]));

                            mWord = mWord + "b";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Bs to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterCImage = (ImageView) findViewById(R.id.letter_c);
        letterCImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[2] > 0) {
                            lettersCaught[2]--;
                            letterCNumber.setText(String.valueOf(lettersCaught[2]));

                            mWord = mWord + "c";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Cs to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterDImage = (ImageView) findViewById(R.id.letter_d);
        letterDImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[3] > 0) {
                            lettersCaught[3]--;
                            letterDNumber.setText(String.valueOf(lettersCaught[3]));

                            mWord = mWord + "d";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Ds to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterEImage = (ImageView) findViewById(R.id.letter_e);
        letterEImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[4] > 0) {
                            lettersCaught[4]--;
                            letterENumber.setText(String.valueOf(lettersCaught[4]));

                            mWord = mWord + "e";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Es to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterFImage = (ImageView) findViewById(R.id.letter_f);
        letterFImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[5] > 0) {
                            lettersCaught[5]--;
                            letterFNumber.setText(String.valueOf(lettersCaught[5]));

                            mWord = mWord + "f";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Fs to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterGImage = (ImageView) findViewById(R.id.letter_g);
        letterGImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[6] > 0) {
                            lettersCaught[6]--;
                            letterGNumber.setText(String.valueOf(lettersCaught[6]));

                            mWord = mWord + "g";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Gs to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterHImage = (ImageView) findViewById(R.id.letter_h);
        letterHImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[7] > 0) {
                            lettersCaught[7]--;
                            letterHNumber.setText(String.valueOf(lettersCaught[7]));

                            mWord = mWord + "h";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Hs to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterIImage = (ImageView) findViewById(R.id.letter_i);
        letterIImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[8] > 0) {
                            lettersCaught[8]--;
                            letterINumber.setText(String.valueOf(lettersCaught[8]));

                            mWord = mWord + "i";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Is to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterJImage = (ImageView) findViewById(R.id.letter_j);
        letterJImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[9] > 0) {
                            lettersCaught[9]--;
                            letterJNumber.setText(String.valueOf(lettersCaught[9]));

                            mWord = mWord + "j";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Js to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterKImage = (ImageView) findViewById(R.id.letter_k);
        letterKImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[10] > 0) {
                            lettersCaught[10]--;
                            letterKNumber.setText(String.valueOf(lettersCaught[10]));

                            mWord = mWord + "k";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Ks to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterLImage = (ImageView) findViewById(R.id.letter_l);
        letterLImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[11] > 0) {
                            lettersCaught[11]--;
                            letterLNumber.setText(String.valueOf(lettersCaught[11]));

                            mWord = mWord + "l";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Ls to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterMImage = (ImageView) findViewById(R.id.letter_m);
        letterMImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[12] > 0) {
                            lettersCaught[12]--;
                            letterMNumber.setText(String.valueOf(lettersCaught[12]));

                            mWord = mWord + "m";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Ms to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterNImage = (ImageView) findViewById(R.id.letter_n);
        letterNImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[13] > 0) {
                            lettersCaught[13]--;
                            letterNNumber.setText(String.valueOf(lettersCaught[13]));

                            mWord = mWord + "n";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Ns to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterOImage = (ImageView) findViewById(R.id.letter_o);
        letterOImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[14] > 0) {
                            lettersCaught[14]--;
                            letterONumber.setText(String.valueOf(lettersCaught[14]));

                            mWord = mWord + "o";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Os to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterPImage = (ImageView) findViewById(R.id.letter_p);
        letterPImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[15] > 0) {
                            lettersCaught[15]--;
                            letterPNumber.setText(String.valueOf(lettersCaught[15]));

                            mWord = mWord + "p";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Ps to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterQImage = (ImageView) findViewById(R.id.letter_q);
        letterQImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[16] > 0) {
                            lettersCaught[16]--;
                            letterQNumber.setText(String.valueOf(lettersCaught[16]));

                            mWord = mWord + "q";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Qs to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterRImage = (ImageView) findViewById(R.id.letter_r);
        letterRImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[17] > 0) {
                            lettersCaught[17]--;
                            letterRNumber.setText(String.valueOf(lettersCaught[17]));

                            mWord = mWord + "r";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Rs to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterSImage = (ImageView) findViewById(R.id.letter_s);
        letterSImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[18] > 0) {
                            lettersCaught[18]--;
                            letterSNumber.setText(String.valueOf(lettersCaught[18]));

                            mWord = mWord + "s";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Ss to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterTImage = (ImageView) findViewById(R.id.letter_t);
        letterTImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[19] > 0) {
                            lettersCaught[19]--;
                            letterTNumber.setText(String.valueOf(lettersCaught[19]));

                            mWord = mWord + "t";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Ts to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterUImage = (ImageView) findViewById(R.id.letter_u);
        letterUImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[20] > 0) {
                            lettersCaught[20]--;
                            letterUNumber.setText(String.valueOf(lettersCaught[20]));

                            mWord = mWord + "u";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Us to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterVImage = (ImageView) findViewById(R.id.letter_v);
        letterVImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[21] > 0) {
                            lettersCaught[21]--;
                            letterVNumber.setText(String.valueOf(lettersCaught[21]));

                            mWord = mWord + "v";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Vs to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterWImage = (ImageView) findViewById(R.id.letter_w);
        letterWImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[22] > 0) {
                            lettersCaught[22]--;
                            letterWNumber.setText(String.valueOf(lettersCaught[22]));

                            mWord = mWord + "w";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Ws to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterXImage = (ImageView) findViewById(R.id.letter_x);
        letterXImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[23] > 0) {
                            lettersCaught[23]--;
                            letterXNumber.setText(String.valueOf(lettersCaught[23]));

                            mWord = mWord + "x";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Xs to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterYImage = (ImageView) findViewById(R.id.letter_y);
        letterYImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[24] > 0) {
                            lettersCaught[24]--;
                            letterYNumber.setText(String.valueOf(lettersCaught[24]));

                            mWord = mWord + "y";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Ys to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        letterZImage = (ImageView) findViewById(R.id.letter_z);
        letterZImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (lettersCaught[25] > 0) {
                            lettersCaught[25]--;
                            letterZNumber.setText(String.valueOf(lettersCaught[25]));

                            mWord = mWord + "z";
                            mTextViewWord.setText(mWord);
                        } else {
                            Toast.makeText(ScrabbleActivity.this, "No Zs to use!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // TODO SET ANIMATION eg. upButton.setImageResource(R.drawable.arrow_up_up);
                        break;
                    }
                }

                return true;
            }
        });

        deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWord != null && mWord != "") {
                    String lastChar = mWord.substring(mWord.length() - 1);
                    switch (lastChar) {
                        case "a":
                            lettersCaught[0]++;
                            letterANumber.setText(String.valueOf(lettersCaught[0]));
                            break;
                        case "b":
                            lettersCaught[1]++;
                            letterBNumber.setText(String.valueOf(lettersCaught[1]));
                            break;
                        case "c":
                            lettersCaught[2]++;
                            letterCNumber.setText(String.valueOf(lettersCaught[2]));
                            break;
                        case "d":
                            lettersCaught[3]++;
                            letterDNumber.setText(String.valueOf(lettersCaught[3]));
                            break;
                        case "e":
                            lettersCaught[4]++;
                            letterENumber.setText(String.valueOf(lettersCaught[4]));
                            break;
                        case "f":
                            lettersCaught[5]++;
                            letterFNumber.setText(String.valueOf(lettersCaught[5]));
                            break;
                        case "g":
                            lettersCaught[6]++;
                            letterGNumber.setText(String.valueOf(lettersCaught[6]));
                            break;
                        case "h":
                            lettersCaught[7]++;
                            letterHNumber.setText(String.valueOf(lettersCaught[7]));
                            break;
                        case "i":
                            lettersCaught[8]++;
                            letterINumber.setText(String.valueOf(lettersCaught[8]));
                            break;
                        case "j":
                            lettersCaught[9]++;
                            letterJNumber.setText(String.valueOf(lettersCaught[9]));
                            break;
                        case "k":
                            lettersCaught[10]++;
                            letterKNumber.setText(String.valueOf(lettersCaught[10]));
                            break;
                        case "l":
                            lettersCaught[11]++;
                            letterLNumber.setText(String.valueOf(lettersCaught[11]));
                            break;
                        case "m":
                            lettersCaught[12]++;
                            letterMNumber.setText(String.valueOf(lettersCaught[12]));
                            break;
                        case "n":
                            lettersCaught[13]++;
                            letterNNumber.setText(String.valueOf(lettersCaught[13]));
                            break;
                        case "o":
                            lettersCaught[14]++;
                            letterONumber.setText(String.valueOf(lettersCaught[14]));
                            break;
                        case "p":
                            lettersCaught[15]++;
                            letterPNumber.setText(String.valueOf(lettersCaught[15]));
                            break;
                        case "q":
                            lettersCaught[16]++;
                            letterQNumber.setText(String.valueOf(lettersCaught[16]));
                            break;
                        case "r":
                            lettersCaught[17]++;
                            letterRNumber.setText(String.valueOf(lettersCaught[17]));
                            break;
                        case "s":
                            lettersCaught[18]++;
                            letterSNumber.setText(String.valueOf(lettersCaught[18]));
                            break;
                        case "t":
                            lettersCaught[19]++;
                            letterTNumber.setText(String.valueOf(lettersCaught[19]));
                            break;
                        case "u":
                            lettersCaught[20]++;
                            letterUNumber.setText(String.valueOf(lettersCaught[20]));
                            break;
                        case "v":
                            lettersCaught[21]++;
                            letterVNumber.setText(String.valueOf(lettersCaught[21]));
                            break;
                        case "w":
                            lettersCaught[22]++;
                            letterWNumber.setText(String.valueOf(lettersCaught[22]));
                            break;
                        case "x":
                            lettersCaught[23]++;
                            letterXNumber.setText(String.valueOf(lettersCaught[23]));
                            break;
                        case "y":
                            lettersCaught[24]++;
                            letterYNumber.setText(String.valueOf(lettersCaught[24]));
                            break;
                        case "z":
                            lettersCaught[25]++;
                            letterZNumber.setText(String.valueOf(lettersCaught[25]));
                            break;
                    }

                    mWord = mWord.substring(0, mWord.length() - 1);
                    mTextViewWord.setText(mWord);
                }
            }
        });

        clearAllButton = (Button) findViewById(R.id.clearAllButton);
        clearAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWord != null && mWord != "") {
                    for (int i = 0; i < mWord.length(); i++) {
                        String mLetter = mWord.substring(i, i+1);
                        switch (mLetter) {
                            case "a":
                                lettersCaught[0]++;
                                letterANumber.setText(String.valueOf(lettersCaught[0]));
                                break;
                            case "b":
                                lettersCaught[1]++;
                                letterBNumber.setText(String.valueOf(lettersCaught[1]));
                                break;
                            case "c":
                                lettersCaught[2]++;
                                letterCNumber.setText(String.valueOf(lettersCaught[2]));
                                break;
                            case "d":
                                lettersCaught[3]++;
                                letterDNumber.setText(String.valueOf(lettersCaught[3]));
                                break;
                            case "e":
                                lettersCaught[4]++;
                                letterENumber.setText(String.valueOf(lettersCaught[4]));
                                break;
                            case "f":
                                lettersCaught[5]++;
                                letterFNumber.setText(String.valueOf(lettersCaught[5]));
                                break;
                            case "g":
                                lettersCaught[6]++;
                                letterGNumber.setText(String.valueOf(lettersCaught[6]));
                                break;
                            case "h":
                                lettersCaught[7]++;
                                letterHNumber.setText(String.valueOf(lettersCaught[7]));
                                break;
                            case "i":
                                lettersCaught[8]++;
                                letterINumber.setText(String.valueOf(lettersCaught[8]));
                                break;
                            case "j":
                                lettersCaught[9]++;
                                letterJNumber.setText(String.valueOf(lettersCaught[9]));
                                break;
                            case "k":
                                lettersCaught[10]++;
                                letterKNumber.setText(String.valueOf(lettersCaught[10]));
                                break;
                            case "l":
                                lettersCaught[11]++;
                                letterLNumber.setText(String.valueOf(lettersCaught[11]));
                                break;
                            case "m":
                                lettersCaught[12]++;
                                letterMNumber.setText(String.valueOf(lettersCaught[12]));
                                break;
                            case "n":
                                lettersCaught[13]++;
                                letterNNumber.setText(String.valueOf(lettersCaught[13]));
                                break;
                            case "o":
                                lettersCaught[14]++;
                                letterONumber.setText(String.valueOf(lettersCaught[14]));
                                break;
                            case "p":
                                lettersCaught[15]++;
                                letterPNumber.setText(String.valueOf(lettersCaught[15]));
                                break;
                            case "q":
                                lettersCaught[16]++;
                                letterQNumber.setText(String.valueOf(lettersCaught[16]));
                                break;
                            case "r":
                                lettersCaught[17]++;
                                letterRNumber.setText(String.valueOf(lettersCaught[17]));
                                break;
                            case "s":
                                lettersCaught[18]++;
                                letterSNumber.setText(String.valueOf(lettersCaught[18]));
                                break;
                            case "t":
                                lettersCaught[19]++;
                                letterTNumber.setText(String.valueOf(lettersCaught[19]));
                                break;
                            case "u":
                                lettersCaught[20]++;
                                letterUNumber.setText(String.valueOf(lettersCaught[20]));
                                break;
                            case "v":
                                lettersCaught[21]++;
                                letterVNumber.setText(String.valueOf(lettersCaught[21]));
                                break;
                            case "w":
                                lettersCaught[22]++;
                                letterWNumber.setText(String.valueOf(lettersCaught[22]));
                                break;
                            case "x":
                                lettersCaught[23]++;
                                letterXNumber.setText(String.valueOf(lettersCaught[23]));
                                break;
                            case "y":
                                lettersCaught[24]++;
                                letterYNumber.setText(String.valueOf(lettersCaught[24]));
                                break;
                            case "z":
                                lettersCaught[25]++;
                                letterZNumber.setText(String.valueOf(lettersCaught[25]));
                                break;
                        }
                    }
                    mWord = "";
                    mTextViewWord.setText(mWord);
                }
            }
        });

        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWord.length() > 2) {
                    if (check_for_word(mWord) && mWord != "" && mWord != null) {
                        mScore = calculateScore(mScore,mWord.length());
                        mWord = "";
                        mTextViewWord.setText(mWord);
                        mScoreTextView.setText("Score: " + String.valueOf(mScore));
                        //Toast.makeText(ScrabbleActivity.this, "+1 point!", Toast.LENGTH_SHORT).show();
                    } else {
                        mWord = "";
                        mTextViewWord.setText(mWord);
                        Toast.makeText(ScrabbleActivity.this, "Not a word!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ScrabbleActivity.this, "3 letters or more!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void setTimer() {
        // Set Game duration
        myCountDownTimer = new CountDownTimer(60000, 1000) {
            TextView mCountDownTimerTextField = (TextView) findViewById(R.id.countdownTimer);

            public void onTick(long millisUntilFinished) {
                mCountDownTimerTextField.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Intent i = new Intent(ScrabbleActivity.this, HighScoreActivity.class);
                i.putExtra(EXTRA_SCRABBLEACTIVITYSCORE, mScore);
                startActivity(i);
                finish();
            }
        }.start();
    }

    private boolean check_for_word(String word) {
        BufferedReader reader = null;
        word = word.toLowerCase();

        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("words.txt")));
            String str;
            while ((str = reader.readLine()) != null) {
                str = str.toLowerCase();
                if (str.contains(word)) {
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return false;
    }

    public int calculateScore(int score, int wordLength) {
        int count = 0;
        for (int i = 1; i <= wordLength; i++) {
            count=count+i;
        }
        score = score + count;
        return score;
    }

    @Override
    protected void onStop() {
        super.onStop();
        myCountDownTimer.cancel();
    }

}
