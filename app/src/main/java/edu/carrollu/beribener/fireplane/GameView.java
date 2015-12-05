package edu.carrollu.beribener.fireplane;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {

    public final int GAME_START_SPEED = 150;

    SimpleGameEngine simpleGameEngine;

    // This is our thread
    Thread gameThread = null;

    // This is new. We need a SurfaceHolder
    // When we use Paint and Canvas in a thread
    // We will see it in action in the drawPlanes method soon.
    SurfaceHolder ourHolder;

    // A boolean which we will set and unset
    // when the game is running- or not.
    volatile boolean playing;

    // A Canvas and a Paint object
    Canvas canvas;
    Paint paint;

    // This variable tracks the game frame rate
    int fps;

    // This is used to help calculate the fps
    private long timeThisFrame;

    int gameCounter = 0;

    // He can walk at 150 pixels per second
    int moveSpeed = 150;

    PlayerPlane playerPlane;
    EnemyPlaneManager enemyPlaneManager;
    FireBallManager fireBallManager;
    BackgroudObjectManager backgroudObjectManager;

    SoundManager soundManager;

    ArrayList<IDrawable> drawables;
    ArrayList<IMoveable> moveables;
    ArrayList<ICollidable> enemyCollidables;

    // When the we initialize (call new()) on gameView
    // This special constructor method runs
    public GameView(SimpleGameEngine context) {
        // The next line of code asks the
        // SurfaceView class to set up our object.
        // How kind.
        super(context);

        this.simpleGameEngine = context;

        // Initialize ourHolder and paint objects
        ourHolder = getHolder();
        paint = new Paint();

        //init our game objects
        playerPlane = new PlayerPlane(this);
        enemyPlaneManager = new EnemyPlaneManager(this);
        fireBallManager = new FireBallManager(this);

        backgroudObjectManager = new BackgroudObjectManager(this);
        ExampleGameItem exampleItem = new ExampleGameItem(this);
        ScoreBox scoreBox = new ScoreBox(this);

        //initialize sound manager
        this.soundManager = new SoundManager(this);

        //initialize drawables
        drawables = new ArrayList<IDrawable>();
        drawables.add(backgroudObjectManager);
        drawables.add(playerPlane);
        drawables.add(enemyPlaneManager);
        //drawables.add(exampleItem);
        drawables.add(fireBallManager);
        drawables.add(scoreBox);


        //initialize moveables
        moveables = new ArrayList<IMoveable>();
        moveables.add(backgroudObjectManager);
        moveables.add(playerPlane);
        moveables.add(enemyPlaneManager);
        moveables.add(fireBallManager);

        //initialize enemyCollidables
        enemyCollidables = new ArrayList<ICollidable>();
        enemyCollidables.add(enemyPlaneManager);


        // Set our boolean to true - game on!
        playing = true;

        //start the background music
        soundManager.playMusic();

    }

    public SoundManager getSoundManager() {
        return this.soundManager;
    }

    public int getCanvasWidth() {
        return ourHolder.getSurfaceFrame().width();
    }

    public int getCanvasHeight() {
        return ourHolder.getSurfaceFrame().height();
    }

    public void onPlaneCollision(Point point) {
        this.playerPlane.destroy();
        Log.d("COLLUSION", point.toString());
    }

    public void onGameOver() {
        this.playing = false;
        this.simpleGameEngine.onGameOver();
    }

    public void dispose() {
        this.backgroudObjectManager.dispose();
        this.soundManager.dispose();
    }

    @Override
    public void run() {
        while (playing) {

            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            // Draw the frame
            draw();

            // Calculate the fps this frame
            // We can then use the result to
            // time animations and more.
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame > 0) {
                fps = 1000 / (int) timeThisFrame;
            }

        }

    }

    // Draw the newly updated scene
    public void draw() {

        // Make sure our drawing surface is valid or we crash
        if (ourHolder.getSurface().isValid()) {
            // Lock the canvas ready to drawPlanes
            canvas = ourHolder.lockCanvas();

            // Draw the background color
            canvas.drawColor(Color.argb(255, 26, 128, 182));

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255, 249, 129, 0));

            // Make the text a bit bigger
            paint.setTextSize(45);

            // Display the current fps on the screen
            canvas.drawText("FPS:" + fps, 20, 40, paint);
            canvas.drawText("POS:" + playerPlane.getX(), 20, 80, paint);

            //move the moveables
            for (IMoveable i : this.moveables)
                i.move();

            //draw the drawables
            for (IDrawable i : this.drawables)
                i.draw();

            //checkcollisions
            for (ICollidable i : this.enemyCollidables) {
                Point collisionPoint = i.doesColllideWith(this.playerPlane);
                if (collisionPoint != null)
                    this.onPlaneCollision(collisionPoint);
            }

            //game speed
            this.moveSpeed = GAME_START_SPEED + enemyPlaneManager.getNumberOfPlanesDismissed() * 10;

            // Draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);

            gameCounter++;
        }

    }

    // If SimpleGameEngine Activity is paused/stopped
    // shutdown our thread.
    public void pause() {
        playing = false;
        this.dispose();
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    // If SimpleGameEngine Activity is started then
    // start our thread.
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    // The SurfaceView class implements onTouchListener
    // So we can override this method and detect screen touches.
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        int direction = Plane.DIRECTION_ZERO;
        boolean fired = false;

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:

                direction = motionEvent.getX() < playerPlane.getX() ? Plane.DIRECTION_LEFT : Plane.DIRECTION_RIGHT;
                fired = motionEvent.getY() < this.getCanvasHeight() / 2;

                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:

                direction = Plane.DIRECTION_ZERO;
                fired = false;
                break;
        }

        if (fired)
            fireBallManager.playerFired();
        else
            playerPlane.setDirectionH(direction);

        return true;
    }

    public int getScore() {
        return this.enemyPlaneManager.getNumberOfPlanesDismissed();
    }

}
