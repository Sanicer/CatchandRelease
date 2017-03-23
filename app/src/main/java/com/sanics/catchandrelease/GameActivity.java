package com.sanics.catchandrelease;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class GameActivity extends AppCompatActivity {

    private boolean isInitialized;
    private ArrayList<Cannonball> cannonBalls;
    private Cannonball createCannonBall, cannonBall;
    private Handler mHandler;
    private int screenWidth, screenHeight;
    private ArrayList<Letter> letters;
    private Letter createLetter, letter;
    private RelativeLayout GameActivity_LayoutView;
    private boolean roundOver = false;
    private float endLetterPoint;
    private ImageView shootButton, upButton, downButton, cannon, letterImg, cannonBallImg;
    private ProgressBar progressBar;
    private double createCannonBallX, createCannonBallY;
    private Point letterPoint = new Point();
    //private Point cannonballPoint = new Point();
    private double cannonballRadius;
    private double letterRadius;
    private int[] lettersCaught = new int[26];
    private static final String EXTRA_GAMEACTIVITY = "com.example.EXTRA_GAMEACTIVITY";
    private static final String EXTRA_GAMEACTIVITYSCORE = "com.example.EXTRA_GAMEACTIVITYSCORE";
    private int letterWidth, letterHeight;
    private int cannonBallWidth, cannonBallHeight;
    private TextView mScoreTextField;
    private int mScore = 0;
    private float mGravity = 0.80f;
    private CountDownTimer myCountDownTimer;
    private boolean cannonCanFire = true, cannonShot = false;
    private double cannonPower = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();

            GameActivity_LayoutView = (RelativeLayout) findViewById(R.id.GameActivity_Layout);
            GameActivity_LayoutView.setBackgroundResource(R.drawable.catchandrelease_bg);
            GameActivity_LayoutView.setPaddingRelative(0, 0, 0, 0);

        }
        isInitialized = false;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (!isInitialized) {
            isInitialized = true;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
            screenHeight = size.y;
            endLetterPoint = (float) size.y * (float) 0.645;
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            Log.d("screenSize", "screenWidth = " + screenWidth);
//            Log.d("screenSize", "screenHeight = " + screenHeight);

            // Set width and height of cannonball to be used in createCannonBall method:
            cannonBallWidth = (screenWidth * 10) / 450;
            cannonBallHeight = (screenWidth * 10) / 450;

            // Get width and height of letter images and find the radius of them:
            letterWidth = (screenWidth * 30) / 450;
            letterHeight = (screenHeight * 30) / 450;
            // Multiply sides of the square by itself
            letterRadius = letterWidth * 2;
            letterRadius = letterWidth * 2 + letterRadius;
            letterRadius = letterHeight * 2 + letterRadius;
            letterRadius = letterHeight * 2 + letterRadius;

            // Get the square of the above result:
            letterRadius = letterRadius * 2;

            // Get the square root of the above result for the diameter:
            letterRadius = Math.sqrt(letterRadius);

            // Divide by 2 to get the radius:
            letterRadius = letterRadius / 2;

            progressBar = (ProgressBar)findViewById(R.id.power_Bar);

            cannon = (ImageView) findViewById(R.id.cannon);
            upButton = (ImageView) findViewById(R.id.arrow_up_btn);
            upButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            //   if (cannon.getRotation() < 66) {
                            cannon.setRotation(cannon.getRotation() + 5);
                            //Log.d("Rotation", "newRotation = " + cannon.getRotation());
                            //  }
                            upButton.setImageResource(R.drawable.arrow_up_down);
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            upButton.setImageResource(R.drawable.arrow_up_up);
                            break;
                        }
                    }

                    return true;
                }
            });

            downButton = (ImageView) findViewById(R.id.arrow_down_btn);
            downButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            //  if (cannon.getRotation() > -10) {
                            cannon.setRotation(cannon.getRotation() - 5);
                            //Log.d("Rotation", "newRotation = " + cannon.getRotation());
                            //  }
                            downButton.setImageResource(R.drawable.arrow_down_down);
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            downButton.setImageResource(R.drawable.arrow_down_up);
                            break;
                        }
                    }

                    return true;
                }
            });

            cannonBalls = new ArrayList<>();
            letters = new ArrayList<>();
            mHandler = new Handler();

            shootButton = (ImageView) findViewById(R.id.shoot_btn);
            shootButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            shootButton.setImageResource(R.drawable.shoot_btn_down);
                            if (cannonCanFire) {
                                shootButton.setPressed(true);
                                cannonShot = true;
                                v.post(shootButtonRunnable);
                            }
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            shootButton.setPressed(false);
                            if (cannonCanFire && cannonShot == true) {
                                cannonCanFire = false;
                                cannonShot = false;
                                createCannonBall(cannon.getRotation(), cannon.getWidth(), cannon.getHeight(), cannonPower);
                                //Log.d("Cannon", "Cannon width: " + cannon.getWidth());
                                //Log.d("Cannon", "Cannon height: " + cannon.getHeight());
                                cannonPower = 0;
                            }
                            shootButton.setImageResource(R.drawable.shoot_btn_up);
                            break;
                        }
                    }

                    return true;
                }
            });

            mScoreTextField = (TextView) findViewById(R.id.score);

            setTimer();
            if (hasFocus) {
                update();
            }
            super.onWindowFocusChanged(hasFocus);
        }
    }

    private void update() {
        Log.d("createLetter", "Is createLetter called? YES");
        Thread thread = new Thread() {
            @Override
            public void run() {
                Log.d("createLetter", "Is run called? YES");
                Random r = new Random();
                int timeTillNextLetter = r.nextInt(30);
                int timeCannonCooldown = 30;

                try {
                    while (!roundOver) {
                        //Log.d("createLetter", "Have we reached the try loop? YES");
                        sleep(50);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                float letterX, letterY;
                                double cannonBallX, cannonBallY;

                                // Move the cannonball
                                for (int i = 0; i < cannonBalls.size(); i++) {
                                    cannonBall = cannonBalls.get(i);

                                    cannonBallX = cannonBall.getX();
                                    cannonBallY = cannonBall.getY();
                                    if (cannonBallX < screenWidth || cannonBallY > 0 || cannonBallX > 0 || cannonBallY < screenHeight) {
                                        cannonBall.setVelocity(mGravity + cannonBall.getVelocity());
                                        cannonBallY += cannonBall.getVelocity();

                                        cannonBallX = cannonBallX + cannonBall.getPower() * (float) sin(cannonBall.getRadRotation());
                                        cannonBallY = cannonBallY - cannonBall.getPower() * (float) cos(cannonBall.getRadRotation());

                                        cannonBall.setX(cannonBallX);
                                        cannonBall.setY(cannonBallY);
                                        cannonBall.getmCannonballImage().setX((float)cannonBallX);
                                        cannonBall.getmCannonballImage().setY((float)cannonBallY);

                                        //cannonballPoint.set((int) cannonBallX, (int) cannonBallY);
                                        //cannonBall.setPoint(cannonballPoint);

                                    }
                                    if (cannonBallX > screenWidth || cannonBallX < 0 || cannonBallY > screenHeight || cannonBallY < -100) {
                                        GameActivity_LayoutView.removeView(cannonBall.getmCannonballImage());
                                        cannonBalls.remove(i);
                                    }
                                }

                                //Log.d("Cannonball", "Cannonballs = " + cannonBalls.size());
                                // Move the letters and Collision detection
                                for (int i = 0; i < letters.size(); i++) {
                                    if (letters.get(i) != null) {
                                        letter = letters.get(i);
                                        letterY = letter.getmLetterImage().getY();
                                        letterX = letter.getmLetterImage().getX();
                                        if (letterY > endLetterPoint) {
                                            GameActivity.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    GameActivity_LayoutView.removeView(letter.getmLetterImage());
                                                }
                                            });
                                            letters.remove(i);
                                        } else {
                                            letterY = letterY + letter.getSpeed();
                                            letter.getmLetterImage().setY(letterY);

                                            letterPoint.set((int) letterX, (int) letterY);
                                            //letterXYArray.set(i, letterPoint);

                                            Point letterXY = new Point();
                                            letterXY.set((int) letterX, (int) letterY);
                                            for (int i2 = 0; i2 < cannonBalls.size(); i2++) {
                                                cannonBall = cannonBalls.get(i2);
                                                // Collision detection of cannonball and letters begins
                                                // Have to add (cannonBallWidth/Height / 2) and (letterWidth/Height / 2) to get the center of the images as image Point is top left
                                                double distance = Math.sqrt((((cannonBall.getX() + (cannonBallWidth / 2)) - (letterX + (letterWidth / 2))) * ((cannonBall.getX() + (cannonBallWidth / 2)) - (letterX + (letterWidth / 2)))) +
                                                        (((cannonBall.getY() + (cannonBallHeight / 2)) - (letterY + (letterHeight / 2))) * ((cannonBall.getY() + (cannonBallHeight / 2)) - (letterY + (letterHeight / 2)))));

                                                if (distance < cannonballRadius + letterRadius) {
                                                    letter.setHit(true);
                                                    //Log.d("Collision", "Cannonball has collided with a letter");
                                                    //Log.d("Collision", "Cannonball points are: " + cannonballPoint.x + " " + cannonballPoint.y);
                                                }
                                            }
                                        }
                                    }
                                }

                                // Check boolean if letters are hit: remove them and add score
                                for (int i = 0; i < letters.size(); i++) {
                                    letter = letters.get(i);
                                    if (letter.isHit()) {
                                        GameActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                GameActivity_LayoutView.removeView(letter.getmLetterImage());
                                            }
                                        });
                                        switch (letter.getmLetterNum()) {
                                            case 0:
                                                if (letter.isGold()) {
                                                    lettersCaught[0] += 3;
                                                } else {
                                                    lettersCaught[0]++;
                                                }
                                                break;
                                            case 1:
                                                if (letter.isGold()) {
                                                    lettersCaught[1] += 3;
                                                } else {
                                                    lettersCaught[1]++;
                                                }
                                                break;
                                            case 2:
                                                if (letter.isGold()) {
                                                    lettersCaught[2] += 3;
                                                } else {
                                                    lettersCaught[2]++;
                                                }
                                                break;
                                            case 3:
                                                if (letter.isGold()) {
                                                    lettersCaught[3] += 3;
                                                } else {
                                                    lettersCaught[3]++;
                                                }
                                                break;
                                            case 4:
                                                if (letter.isGold()) {
                                                    lettersCaught[4] += 3;
                                                } else {
                                                    lettersCaught[4]++;
                                                }
                                                break;
                                            case 5:
                                                if (letter.isGold()) {
                                                    lettersCaught[5] += 3;
                                                } else {
                                                    lettersCaught[5]++;
                                                }
                                                break;
                                            case 6:
                                                if (letter.isGold()) {
                                                    lettersCaught[6] += 3;
                                                } else {
                                                    lettersCaught[6]++;
                                                }
                                                break;
                                            case 7:
                                                if (letter.isGold()) {
                                                    lettersCaught[7] += 3;
                                                } else {
                                                    lettersCaught[7]++;
                                                }
                                                break;
                                            case 8:
                                                if (letter.isGold()) {
                                                    lettersCaught[8] += 3;
                                                } else {
                                                    lettersCaught[8]++;
                                                }
                                                break;
                                            case 9:
                                                if (letter.isGold()) {
                                                    lettersCaught[9] += 3;
                                                } else {
                                                    lettersCaught[9]++;
                                                }
                                                break;
                                            case 10:
                                                if (letter.isGold()) {
                                                    lettersCaught[10] += 3;
                                                } else {
                                                    lettersCaught[10]++;
                                                }
                                                break;
                                            case 11:
                                                if (letter.isGold()) {
                                                    lettersCaught[11] += 3;
                                                } else {
                                                    lettersCaught[11]++;
                                                }
                                                break;
                                            case 12:
                                                if (letter.isGold()) {
                                                    lettersCaught[12] += 3;
                                                } else {
                                                    lettersCaught[12]++;
                                                }
                                                break;
                                            case 13:
                                                if (letter.isGold()) {
                                                    lettersCaught[13] += 3;
                                                } else {
                                                    lettersCaught[13]++;
                                                }
                                                break;
                                            case 14:
                                                if (letter.isGold()) {
                                                    lettersCaught[14] += 3;
                                                } else {
                                                    lettersCaught[14]++;
                                                }
                                                break;
                                            case 15:
                                                if (letter.isGold()) {
                                                    lettersCaught[15] += 3;
                                                } else {
                                                    lettersCaught[15]++;
                                                }
                                                break;
                                            case 16:
                                                if (letter.isGold()) {
                                                    lettersCaught[16] += 3;
                                                } else {
                                                    lettersCaught[16]++;
                                                }
                                                break;
                                            case 17:
                                                if (letter.isGold()) {
                                                    lettersCaught[17] += 3;
                                                } else {
                                                    lettersCaught[17]++;
                                                }
                                                break;
                                            case 18:
                                                if (letter.isGold()) {
                                                    lettersCaught[18] += 3;
                                                } else {
                                                    lettersCaught[18]++;
                                                }
                                                break;
                                            case 19:
                                                if (letter.isGold()) {
                                                    lettersCaught[19] += 3;
                                                } else {
                                                    lettersCaught[19]++;
                                                }
                                                break;
                                            case 20:
                                                if (letter.isGold()) {
                                                    lettersCaught[20] += 3;
                                                } else {
                                                    lettersCaught[20]++;
                                                }
                                                break;
                                            case 21:
                                                if (letter.isGold()) {
                                                    lettersCaught[21] += 3;
                                                } else {
                                                    lettersCaught[21]++;
                                                }
                                                break;
                                            case 22:
                                                if (letter.isGold()) {
                                                    lettersCaught[22] += 3;
                                                } else {
                                                    lettersCaught[22]++;
                                                }
                                                break;
                                            case 23:
                                                if (letter.isGold()) {
                                                    lettersCaught[23] += 3;
                                                } else {
                                                    lettersCaught[23]++;
                                                }
                                                break;
                                            case 24:
                                                if (letter.isGold()) {
                                                    lettersCaught[24] += 3;
                                                } else {
                                                    lettersCaught[24]++;
                                                }
                                                break;
                                            case 25:
                                                if (letter.isGold()) {
                                                    lettersCaught[25] += 3;
                                                } else {
                                                    lettersCaught[25]++;
                                                }
                                                break;
                                        }

                                        letters.remove(i);
                                        mScore++;
                                        mScoreTextField.setText("Score: " + String.valueOf(mScore));
                                    }
                                }
                            }
                        });
                        if (timeTillNextLetter != 0) {
                            timeTillNextLetter--;
                        }
                        if (timeTillNextLetter == 0) {
                            GameActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    createLetter();
                                }
                            }); timeTillNextLetter = r.nextInt(30);
                        }

                        if (timeCannonCooldown != 0 && cannonCanFire == false) {
                            timeCannonCooldown--;
                        }
                        if (timeCannonCooldown == 0) {
                            cannonCanFire = true;
                            progressBar.setProgress(0);
                            timeCannonCooldown = 30;
                        }

                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
        thread.start();

    }

    private void createLetter() {
        Random r = new Random();
        int letterIndex = r.nextInt(31);
        int letterSpeed = r.nextInt(10-4) + 4;

        letterImg = new ImageView(this);
        int randomForGold = r.nextInt(15);
        if (randomForGold == 1) {
            createLetter = new Letter(letterImg, letterIndex, letterSpeed, true);
        } else {
            createLetter = new Letter(letterImg, letterIndex, letterSpeed, false);
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                letterWidth, letterHeight);

        switch (letterIndex) {
            case 0:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_a);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_a);
                }
                break;
            case 1:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_b);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_b);
                }
                break;
            case 2:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_c);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_c);
                }
                break;
            case 3:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_d);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_d);
                }
                break;
            case 4:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_e);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_e);
                }
                break;
            case 5:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_f);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_f);
                }
                break;
            case 6:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_g);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_g);
                }
                break;
            case 7:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_h);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_h);
                }
                break;
            case 8:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_i);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_i);
                }
                break;
            case 9:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_j);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_j);
                }
                break;
            case 10:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_k);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_k);
                }
                break;
            case 11:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_l);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_l);
                }
                break;
            case 12:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_m);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_m);
                }
                break;
            case 13:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_n);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_n);
                }
                break;
            case 14:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_o);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_o);
                }
                break;
            case 15:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_p);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_p);
                }
                break;
            case 16:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_q);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_q);
                }
                break;
            case 17:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_r);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_r);
                }
                break;
            case 18:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_s);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_s);
                }
                break;
            case 19:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_t);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_t);
                }
                break;
            case 20:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_u);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_u);
                }
                break;
            case 21:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_v);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_v);
                }
                break;
            case 22:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_w);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_w);
                }
                break;
            case 23:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_x);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_x);
                }
                break;
            case 24:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_y);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_y);
                }
                break;
            case 25:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_z);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_z);
                }
                break;
            case 26:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_a);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_a);
                }
                break;
            case 27:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_e);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_e);
                }
                break;
            case 28:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_i);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_i);
                }
                break;
            case 29:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_o);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_o);
                }
                break;
            case 30:
                if (randomForGold == 1) {
                    createLetter.getmLetterImage().setImageResource(R.drawable.lettergold_u);
                } else {
                    createLetter.getmLetterImage().setImageResource(R.drawable.letter_u);
                }
                break;
        }
        createLetter.getmLetterImage().setLayoutParams(layoutParams);

        int randomXPoint = r.nextInt(screenWidth - letterWidth);

        createLetter.getmLetterImage().setX(randomXPoint);
        createLetter.getmLetterImage().setY(0);
        GameActivity_LayoutView.addView(createLetter.getmLetterImage());
        letters.add(createLetter);
        //letterXYArray.add(null);
        //Log.d("LetterCreated", "LetterXYArray size is: " + letterXYArray.size());
    }

    public void createCannonBall(float cannonRotation, int cannonWidth, int cannonHeight, double power) {
        cannonBallImg = new ImageView(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                cannonBallWidth, cannonBallHeight);

        cannonBallImg.setImageResource(R.drawable.ball);
        cannonBallImg.setLayoutParams(layoutParams);
        // BEGIN THE CANNONBALL ROTATION CALC

        // coordinates of point - centre of square (pulls back to 0,0)
        // cannonWidth / 2 - cannonBallWidth / 2 is to get middle X point
        float tempX = (cannonWidth / 2 - cannonBallWidth / 2) - (cannonWidth / 2 - cannonBallWidth / 2);
        // cannonHeight + cannonBallHeight is to get top of square
        float tempY = (cannonHeight) - (cannonHeight / 2);

        // -1 to turn clockwise and then to change degrees to radian
        double radianCannonRotation = -1 * (cannonRotation * PI / 180);
        // formulae to rotate square at 0,0
        double rotatedX = tempX * cos(radianCannonRotation) - tempY * sin(radianCannonRotation);
        double rotatedY = tempX * sin(radianCannonRotation) + tempY * cos(radianCannonRotation);

        // re-add so new rotated coords are no longer at 0,0
        createCannonBallX = rotatedX + (cannonWidth / 2 - cannonBallWidth / 2);
        double y = rotatedY + (cannonHeight / 2);
        // -17 because calculation is weird somewhere. 17 was found through trial testing.
        createCannonBallY = screenHeight - y - 17;
        // END THE CANNONBALL ROTATION CALC

        cannonBallImg.setX((float) createCannonBallX);
        cannonBallImg.setY((float) createCannonBallY);
        GameActivity_LayoutView.addView(cannonBallImg);

        cannonballRadius = Math.sqrt((cannonBallWidth * cannonBallWidth) + (cannonBallHeight * cannonBallHeight));
//            Log.d("CannonBallRadius", "cannonBallWidth = " + cannonBallWidth);
//            Log.d("CannonBallRadius", "cannonBallHeight = " + cannonBallHeight);
//            Log.d("CannonBallRadius", "cannonballRadius = " + cannonballRadius);
        //Log.d("Cannon", "Cannon Rotation:" + cannonRotation + " and Cannon Width:" + cannonWidth + " and Cannon Height:" + cannonHeight);
        //Log.d("Cannon", "X axis is: " + x + " and Y axis is: " + y);
        //Log.d("Cannon", "Screen width: " + screenWidth + " and Screen height: " + screenHeight);

        createCannonBall = new Cannonball(cannonBallImg, createCannonBallX, createCannonBallY, cannon.getRotation() * PI / 180, power);
        cannonBalls.add(createCannonBall);
    }

    private void setTimer() {
        // Set Game duration
        myCountDownTimer = new CountDownTimer(60000, 1000) {
            TextView mCountDownTimerTextField = (TextView) findViewById(R.id.countdownTimer);

            public void onTick(long millisUntilFinished) {
                mCountDownTimerTextField.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                roundOver = true;
                Intent i = new Intent(GameActivity.this, ScrabbleActivity.class);
                i.putExtra(EXTRA_GAMEACTIVITY, lettersCaught);
                i.putExtra(EXTRA_GAMEACTIVITYSCORE, mScore);
                startActivity(i);
                finish();
            }
        }.start();

//        final int interval = 40000; // 40 seconds
//
//        Runnable runnable = new Runnable() {
//            public void run() {
//                //Toast.makeText(GameActivity.this, "Round finished!", Toast.LENGTH_SHORT).show();
//                roundOver = true;
//                Intent i = new Intent(GameActivity.this, ScrabbleActivity.class);
//                i.putExtra(EXTRA_GAMEACTIVITY, lettersCaught);
//                startActivity(i);
//                finish();
//            }
//        };
//
//        mHandler.postAtTime(runnable, System.currentTimeMillis() + interval);
//        mHandler.postDelayed(runnable, interval);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacksAndMessages(null);
        roundOver = true;
        myCountDownTimer.cancel();
    }

    private Runnable shootButtonRunnable = new Runnable() {
        @Override
        public void run() {
            if (shootButton.isPressed()) {
                progressBar.incrementProgressBy(20);
                if (cannonPower < 50) {
                    switch(progressBar.getProgress()) {
                        case 20:
                            cannonPower = 20;
                            break;
                        case 40:
                            cannonPower = 30;
                            break;
                        case 60:
                            cannonPower = 40;
                            break;
                        case 80:
                            cannonPower = 45;
                            break;
                        case 100:
                            cannonPower = 50;
                            break;
                    }

                    //cannonPower = cannonPower + 15;
                }
                shootButton.postDelayed(shootButtonRunnable, 250);
            }
        }
    };
}
