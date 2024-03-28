package com.gamecodeschool.snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
public class gameVisuals {
    Canvas mCanvas;
    Paint mPaint;
    private Rect pauseButton;
    public gameVisuals(Canvas canvas,Paint paint){
        mCanvas = canvas;
        mPaint = paint;

    }

    public void drawScreen(int score){
        // Fill the screen with a color
        mCanvas.drawColor(Color.BLACK);

        // Set the size and color of the mPaint for the text
        mPaint.setColor(Color.argb(255, 255, 255, 255));
        mPaint.setTextSize(75);

        // Draw the score
        mCanvas.drawText("Score: ",10,120,mPaint);
        mCanvas.drawText(String.valueOf(score), 250, 120, mPaint);
    }

    public void drawStars(int w, int h){
        mPaint.setColor(Color.WHITE);
        int numOfStars = 50;
        while (numOfStars > 0){
            mCanvas.drawCircle((int) (Math.random() * w), (int) (Math.random() * h), 2, mPaint);
            numOfStars--;
        }
    }

    public void drawPlanets(int w, int h){
        mPaint.setColor(Color.GREEN);
        mCanvas.drawCircle(w,h,4,mPaint);
    }

    public void drawPause(boolean mPaused, int w){
        int leftX = w - 320;
        int rightX = mPaused ? (leftX + 250) : (leftX + 210);
        int top = 50;
        int bottom = 150;
        //drawing the pause button to implement pause function on screen during game
        pauseButton = new Rect(leftX, top, rightX, bottom);
        mPaint.setColor(Color.argb(150,255,153,51)); //orange button color
        mCanvas.drawRect(pauseButton,mPaint);
        mPaint.setColor(Color.argb(255,0,0,0)); //black txt color
        mPaint.setTextSize(60);
        String buttonTxt = mPaused ? "Resume" : "Pause";
        mCanvas.drawText(buttonTxt,(leftX+20),120,mPaint);
    }

    public void drawText(){
        // Set the size and color of the mPaint for the text
        mPaint.setColor(Color.argb(255, 255, 255, 255));
        mPaint.setTextSize(150);

        mCanvas.drawText("Tap To Play!", 182, 700, mPaint);

        mPaint.setTextSize(50);
        mCanvas.drawText("GUIDE:",460,800,mPaint);
        mCanvas.drawText("Collect the apples",330,880,mPaint);
        mCanvas.drawText("Collect the blinking apple for double points",70,940,mPaint);
        mCanvas.drawText("If snake reaches end of screen, or",180,1000,mPaint);
        mCanvas.drawText("If snake eats itself, then",280,1060,mPaint);
        mCanvas.drawText("GAME OVER",400,1120,mPaint);
    }

    public boolean isClicked(int touchX, int touchY){
        return pauseButton.contains(touchX,touchY);
    }
}
