package com.gamecodeschool.snake;

import android.content.Context;
import android.graphics.Point;
import java.util.Random;

public class Apple extends AbstractApple {
    public Apple(Context context, Point sr, int s){
        super(context, sr, s);
    }

    // This is called every time an apple is eaten
    @Override
    public void spawn(){
        // Choose two random values and place the apple
        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;
    }


}