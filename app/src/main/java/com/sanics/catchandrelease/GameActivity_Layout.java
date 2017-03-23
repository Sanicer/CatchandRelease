package com.sanics.catchandrelease;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Sanic on 30/05/2016.
 */
public class GameActivity_Layout extends SurfaceView implements Runnable {
    Thread thread;
    SurfaceHolder surfaceHolder;
    Canvas canvas;
    boolean canDraw;

    public GameActivity_Layout(Context context) {
        super(context);
    }

    @Override
    public void run() {

    }

    public void pause() {
        canDraw = false;

        while (true) {
            try {
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        thread = null;

    }

    public void resume() {
        canDraw = true;
        thread = new Thread(this);
        thread.start();
    }
}
