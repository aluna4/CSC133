package com.gamecodeschool.snake;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import java.util.Random;

class Apple implements gameObjects{

    // The location of the apple on the grid
    // Not in pixels
    private final Point location = new Point();

    // The range of values we can choose from
    // to spawn an apple
    //private Point mSpawnRange;
    private final int mSize;

    // An image to represent the apple
    private Bitmap mBitmapApple;

    /// Set up the apple in the constructor
    Apple(Context context,Point sr, int s){

        // Make a note of the passed in spawn range
       // mSpawnRange = sr;
        // Make a note of the size of an apple
        mSize = s;
        // Hide the apple off-screen until the game starts
        location.x = -10;

        // Load the image to the bitmap
        mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);
        mBitmapApple = Bitmap.createScaledBitmap(mBitmapApple, s, s, false);
    }

    // This is called every time an apple is eaten
    @Override
    public void reset(int x, int y){
        // Choose two random values and place the apple
        Random random = new Random();
        location.x = random.nextInt(x) + 1;
        location.y = random.nextInt(y - 1) + 1;
    }

    // SnakeGame can share this with the snake
    @Override
    public Point getLocation(){
        return location;
    }

    // Draw the apple
    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmapApple,
                location.x * mSize, location.y * mSize, paint);

    }

    public int getmSize(){return mSize;}

    public Bitmap getmBitmapApple(){return mBitmapApple;}
}