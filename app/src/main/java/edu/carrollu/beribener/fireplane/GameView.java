package edu.carrollu.beribener.fireplane;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {

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

    // He can walk at 150 pixels per second
    int moveSpeed = 150;

    PlayerPlane playerPlane;
    EnemyPlaneManager enemyPlaneManager;

    ArrayList<IDrawable> drawables;
    ArrayList<IMoveable> moveables;
    ArrayList<ICollidable> collidables;

    // When the we initialize (call new()) on gameView
    // This special constructor method runs
    public GameView(Context context) {
        // The next line of code asks the
        // SurfaceView class to set up our object.
        // How kind.
        super(context);

        // Initialize ourHolder and paint objects
        ourHolder = getHolder();
        paint = new Paint();

        //init our game objects
        playerPlane = new PlayerPlane(this);
        enemyPlaneManager = new EnemyPlaneManager(this);
        CloudManager cloudManager = new CloudManager(this);
        ExampleGameItem exampleItem = new ExampleGameItem(this);

        //initialize drawables
        drawables = new ArrayList<IDrawable>();
        drawables.add(cloudManager);
        drawables.add(playerPlane);
        drawables.add(enemyPlaneManager);
        drawables.add(exampleItem);


        //initialize moveables
        moveables = new ArrayList<IMoveable>();
        moveables.add(cloudManager);
        moveables.add(playerPlane);
        moveables.add(enemyPlaneManager);

        //initialize collidables
        collidables = new ArrayList<ICollidable>();
        collidables.add(enemyPlaneManager);


        // Set our boolean to true - game on!
        playing = true;

    }

    public int getCanvasWidth() {
        return ourHolder.getSurfaceFrame().width();
    }

    public int getCanvasHeight() {
        return ourHolder.getSurfaceFrame().height();
    }

    public void onPlaneCollision(Point point) {
        Log.d("COLLUSION", point.toString());
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

            //checkcollusions
            for (ICollidable i : this.collidables) {
                Point collisionPoint = i.doesColllideWith(this.playerPlane);
                if (collisionPoint != null)
                    this.onPlaneCollision(collisionPoint);
            }

            // Draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);
        }

    }

    // If SimpleGameEngine Activity is paused/stopped
    // shutdown our thread.
    public void pause() {
        playing = false;
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

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:

                direction = motionEvent.getX() < playerPlane.getX() ? Plane.DIRECTION_LEFT : Plane.DIRECTION_RIGHT;
                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:

                direction = Plane.DIRECTION_ZERO;

                break;
        }

        playerPlane.setDirectionH(direction);

        return true;
    }

}
