package com.gamecodeschool.snake;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import java.util.Random;

class Apple implements gameObjects{
    private final Point location = new Point();
    //private final Point mSpawnRange;
    private final int mSize;
    private Bitmap mBitmapApple;

    Apple(Context context, Point sr, int s){
        //mSpawnRange = sr;
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

}