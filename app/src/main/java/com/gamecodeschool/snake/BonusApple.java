package com.gamecodeschool.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class BonusApple extends Apple {
    private boolean isBlinking;
    private long blinkStartTime;
    private long blinkDuration = 1000; // Milliseconds

    public BonusApple(Context context, Point sr, int s) {
        super(context, sr, s); // Mark as a bonus apple
    }

    // Method to update the blinking state of the bonus apple
    private void updateBlinking(long currentTime) {
        if (!isBlinking) {
            blinkStartTime = currentTime;
            isBlinking = true;
        } else if (currentTime - blinkStartTime >= blinkDuration) {
            isBlinking = false;
        }
    }

    // Method to draw the bonus apple on the canvas

    public void draw(Canvas canvas, Paint paint, long currentTime) {
        updateBlinking(currentTime); // Update blinking state
        if (isBlinking && (currentTime - blinkStartTime) % 1000 < 500) {
            // Draw the bonus apple only during the first half of each second (for blinking effect)
            canvas.drawBitmap(mBitmapApple, location.x * mSize, location.y * mSize, paint);
        } else if (!isBlinking) {
            // Draw the bonus apple normally
            canvas.drawBitmap(mBitmapApple, location.x * mSize, location.y * mSize, paint);
        }
    }
}
