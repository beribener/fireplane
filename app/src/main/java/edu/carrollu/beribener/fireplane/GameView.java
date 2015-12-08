package edu.carrollu.beribener.fireplane;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Game framework inspired by:
 * http://gamecodeschool.com/android/coding-android-sprite-sheet-animations/
 */
public class GameView extends SurfaceView implements Runnable {

    public final int GAME_START_SPEED = 150;

    GameEngine gameEngine;

    Thread gameThread = null;

    SurfaceHolder surfaceHolder;

    //indicates if the game is running or not
    volatile boolean playing;

    //canvas to draw objects to
    Canvas canvas;

    //default paint
    Paint paint;

    //track frame rate
    int fps;

    //duration for this frame to be drawn on screen
    private long timeThisFrame;

    //internal counter to mimic timer behaviour in the same thread
    int gameCounter = 0;

    //initial game speed
    int moveSpeed = 150;

    //game elements
    PlayerPlane playerPlane;
    EnemyPlaneManager enemyPlaneManager;
    FireBallManager fireBallManager;
    BackgroudObjectManager backgroudObjectManager;
    SoundManager soundManager;

    ArrayList<IDrawable> drawables;
    ArrayList<IMoveable> moveables;
    ArrayList<ICollidable> enemyCollidables;


    public GameView(GameEngine context) {

        super(context);

        this.gameEngine = context;

        surfaceHolder = getHolder();
        this.paint = new Paint();

        //init our game objects
        playerPlane = new PlayerPlane(this);
        enemyPlaneManager = new EnemyPlaneManager(this);
        fireBallManager = new FireBallManager(this);
        backgroudObjectManager = new BackgroudObjectManager(this);
        //ExampleGameItem exampleItem = new ExampleGameItem(this);
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

        playing = true;

        //start the background music
        soundManager.playMusic();

    }

    public SoundManager getSoundManager() {
        return this.soundManager;
    }

    public int getCanvasWidth() {
        return surfaceHolder.getSurfaceFrame().width();
    }

    public int getCanvasHeight() {
        return surfaceHolder.getSurfaceFrame().height();
    }

    public void onPlaneCollision(Point point) {
        this.playerPlane.destroy();
        Log.d("COLLUSION", point.toString());
    }

    public void onGameOver() {
        this.playing = false;
        this.gameEngine.onGameOver();
    }

    public void dispose() {
        this.backgroudObjectManager.dispose();
        this.soundManager.dispose();
    }

    @Override
    public void run() {
        while (playing) {

            //get current time
            long startFrameTime = System.currentTimeMillis();

            // Draw the frame
            draw();

            //calculate drawing duration for this frame
            timeThisFrame = System.currentTimeMillis() - startFrameTime;

            //calculate fps
            if (timeThisFrame > 0) {
                fps = 1000 / (int) timeThisFrame;
            }

        }

    }

    //draws all game elements in the screen
    public void draw() {

        if (surfaceHolder.getSurface().isValid()) {

            //lock the canvas
            canvas = surfaceHolder.lockCanvas();

            //draw background color
            canvas.drawColor(Color.argb(255, 26, 128, 182));

            // Choose the brush color for drawing
            /*paint.setColor(Color.argb(255, 249, 129, 0));

            // Make the text a bit bigger
            paint.setTextSize(45);

            // Display the current fps on the screen
            canvas.drawText("FPS:" + fps, 20, 40, paint);
            canvas.drawText("POS:" + playerPlane.getX(), 20, 80, paint);*/

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

            //adjust game speed according to planes dismissed
            this.moveSpeed = GAME_START_SPEED + enemyPlaneManager.getNumberOfPlanesDismissed() * 10;

            // Draw everything to the screen
            surfaceHolder.unlockCanvasAndPost(canvas);

            gameCounter++;
        }

    }

    public void pause() {
        playing = false;
        this.dispose();
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    //used to track player input
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        int direction = Plane.DIRECTION_ZERO;
        boolean fired = false;

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:

                //determine direction by relative position of touch to plane
                direction = motionEvent.getX() < playerPlane.getX() ? Plane.DIRECTION_LEFT : Plane.DIRECTION_RIGHT;

                //player plane fires when upper half of the screen is clicked
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

    //returns the current score in the game
    public int getScore() {
        return this.enemyPlaneManager.getNumberOfPlanesDismissed();
    }

}
