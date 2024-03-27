package com.gamecodeschool.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

abstract class AbstractApple {
    // The location of the apple on the grid
    // Not in pixels
    protected Point location = new Point();

    // The range of values we can choose from
    // to spawn an apple
    protected Point mSpawnRange;
    protected int mSize;

    // An image to represent the apple
    protected Bitmap mBitmapApple;

    /// Set up the apple in the constructor
    public AbstractApple(Context context, Point sr, int s){
        // Make a note of the passed in spawn range
        this.mSpawnRange = sr;
        // Make a note of the size of an apple
        this.mSize = s;
        // Hide the apple off-screen until the game starts
        this.location.x = -10;
        // Load the image to the bitmap
        mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);
        // Resize the bitmap
        mBitmapApple = Bitmap.createScaledBitmap(mBitmapApple, s, s, false);
    }

    // This is called every time an apple is eaten
    public void spawn(){
    }

    // Let SnakeGame know where the apple is
    // SnakeGame can share this with the snake
    public Point getLocation(){
        return location;
    }

    // Draw the apple
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmapApple,
                location.x * mSize, location.y * mSize, paint);

    }
}
