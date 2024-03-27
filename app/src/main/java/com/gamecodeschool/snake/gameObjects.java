package com.gamecodeschool.snake;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public interface gameObjects {
    Canvas canvas = new Canvas();
    Paint paint = new Paint();
    void draw(Canvas canvas, Paint paint);
    Point getLocation();
    void reset(int x, int y);
}

