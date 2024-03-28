package com.gamecodeschool.snake;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;

@SuppressLint("ViewConstructor")
class SnakeGame extends SurfaceView implements Runnable{
    private Thread mThread = null;
    private long mNextFrameTime;
    private volatile boolean mPlaying = false;
    private volatile boolean mPaused = true;

    // for playing sound effects
    private final SoundPool mSP;
    private int mEat_ID = -1;
    private int mCrashID = -1;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private final int mNumBlocksHigh;
    private int mScore;
    private final SurfaceHolder mSurfaceHolder;
    private final Paint mPaint;
    private final Point loc;
    private final Snake mSnake;
    // And an apple
    private final Apple mApple;
    private final BonusApple mBonusApple;
    private gameVisuals vis;

    public SnakeGame(Context context, Point size) {
        super(context);

        // Work out how many pixels each block is
        int blockSize = size.x / NUM_BLOCKS_WIDE;
        mNumBlocksHigh = size.y / blockSize;

        // Initialize the SoundPool
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        mSP = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("get_apple.ogg");
            mEat_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("snake_death.ogg");
            mCrashID = mSP.load(descriptor, 0);

        } catch (IOException e) {
            //error
        }

        // Initialize the drawing objects
        mSurfaceHolder = getHolder();
        mPaint = new Paint();
        loc = new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh);

        // Call the constructors of our two game objects
        mApple = new Apple(context,loc,
                blockSize);
        mBonusApple = new BonusApple(context,
                new Point(loc.x,loc.y), blockSize);

        mSnake = new Snake(context,loc,
                blockSize);

    }

    public void newGame() {
        mSnake.reset(NUM_BLOCKS_WIDE, mNumBlocksHigh);
        mApple.reset(loc.x,loc.y);
        mBonusApple.reset(loc.x,loc.y);
        mScore = 0;
        mNextFrameTime = System.currentTimeMillis();
    }

    // Handles the game loop
    @Override
    public void run() {
        while (mPlaying) {
            if(!mPaused) {
                // Update 10 times a second
                if (updateRequired()) {
                    update();
                }
            }
            draw();
        }
    }


    // Check to see if it is time for an update
    public boolean updateRequired() {

        // Run at 10 frames per second
        final long TARGET_FPS = 10;
        final long MILLIS_PER_SECOND = 1000;

        // Are we due to update the frame
        if(mNextFrameTime <= System.currentTimeMillis()){
            mNextFrameTime =System.currentTimeMillis()
                    + MILLIS_PER_SECOND / TARGET_FPS;
            return true;
        }
        return false;
    }

    // Update all the game objects
    public void update() {
        mSnake.move();

        // Did the head of the snake eat the apple?
        if(mSnake.checkDinner(mApple.getLocation())){
            mApple.reset(loc.x,loc.y);
            mScore = mScore + 1;

            // Play a sound
            mSP.play(mEat_ID, 1, 1, 0, 0, 1);
        }
        // Check if the snake eats the bonus apple
        if (mSnake.checkDinner(mBonusApple.getLocation())) {
            mScore += 2;
            mSP.play(mEat_ID, 1, 1, 0, 0, 1);
            // Respawn the bonus apple
            mBonusApple.reset(loc.x,loc.y);
        }
        // Did the snake die?
        if (mSnake.detectDeath()) {
            // Pause the game ready to start again
            mSP.play(mCrashID, 1, 1, 0, 0, 1);
            mPaused =true;
            newGame();
        }

    }

    public void draw() {
        // Get a lock on the mCanvas
        if (mSurfaceHolder.getSurface().isValid()) {
            Canvas mCanvas = mSurfaceHolder.lockCanvas();
            vis = new gameVisuals(mCanvas, mPaint);
            vis.drawScreen(mScore);

            mApple.draw(mCanvas, mPaint);
            mSnake.draw(mCanvas, mPaint);
            mBonusApple.draw(mCanvas, mPaint, System.currentTimeMillis());

            vis.drawPause(mPaused,getWidth());
            vis.drawStars(getWidth(),getHeight());
            for(int i=0; i<10;i++){
                int x = (int) (Math.random() * getWidth());
                int y = (int) (Math.random() * getHeight());
                vis.drawPlanets(x,y);
            }

            // Draw some text while paused
            if(mPaused){
                vis.drawText();
            }

            // Unlock the mCanvas and reveal the graphics for this frame
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        // Let the Snake class handle the input
        if ((motionEvent.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
            if (vis.isClicked(x, y)) {
                mPaused = !mPaused;

                // Don't want to process snake direction for this tap
                return true;
            } else if (mPaused) {
                mPaused = false;
                newGame();
                return true;
            } else {
                mSnake.switchHeading(motionEvent);
            }
        }
        return true;
    }

    // Stop the thread
    public void pause() {
        mPlaying = false;
        try {
            mThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    // Start the thread
    public void resume() {
        mPlaying = true;
        mThread = new Thread(this);
        mThread.start();
    }
}
