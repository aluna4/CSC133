package com.gamecodeschool.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class BonusApple extends Apple {
    private boolean isBlinking;
    private long blinkStartTime;

    public BonusApple(Context context, Point sr, int s) {
        super(context, sr, s); // Mark as a bonus apple
    }

    // Method to update the blinking state of the bonus apple
    private void updateBlinking(long currentTime) {
        // Milliseconds
        long blinkDuration = 1000;
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
            canvas.drawBitmap(getmBitmapApple(), getLocation().x * getmSize(), getLocation().y * getmSize(), paint);
        } else if (!isBlinking) {
            // Draw the bonus apple normally
            canvas.drawBitmap(getmBitmapApple(), getLocation().x * getmSize(), getLocation().y * getmSize(), paint);
        }
    }
}
