package com.sanics.catchandrelease;

import android.graphics.Point;
import android.widget.ImageView;

/**
 * Created by Sanic on 30/09/2016.
 */

public class Cannonball {

    private ImageView mCannonballImage;
    private double X;
    private double Y;
    private float velocity;
    private double power;
    private double radRotation;

    public Cannonball (ImageView letterImg, double cannonBallX, double cannonBallY, double cannonRadRotation, double cannonPower) {
        this.mCannonballImage = letterImg;
        this.X = cannonBallX;
        this.Y = cannonBallY;
        this.radRotation = cannonRadRotation;
        this.velocity = 0;
        this.power = cannonPower;
    }

    public ImageView getmCannonballImage() {
        return mCannonballImage;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setX(double x) {
        X = x;
    }

    public void setY(double y) {
        Y = y;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public double getRadRotation() {
        return radRotation;
    }

    public double getPower() {
        return power;
    }
}



