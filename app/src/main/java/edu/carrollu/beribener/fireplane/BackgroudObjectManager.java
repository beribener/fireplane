package edu.carrollu.beribener.fireplane;


import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by beribener on 11/11/15.
 */
public class BackgroudObjectManager implements IMoveable {

    private GameView gameView;
    private ArrayList<BackgroundObject> backgroundObjects;
    private ArrayList<BackgroundObject> clouds;
    private ArrayList<BackgroundObject> islands;

    private final int MAX_OBJECTS = 10;
    private final int MAX_DISPATCH_INTERVAL = 1000;

    //handler for dispatching of backgroundObjects
    private Handler dispatchHandler;
    private Runnable objectCreator;

    public BackgroudObjectManager(GameView gameView) {

        this.gameView = gameView;
        backgroundObjects = new ArrayList<BackgroundObject>();
        clouds = new ArrayList<BackgroundObject>();
        islands = new ArrayList<BackgroundObject>();

        objectCreator = new ObjectCreator();

        dispatchHandler = new Handler();
        dispatchHandler.postDelayed(objectCreator, MAX_DISPATCH_INTERVAL);

    }

    public void draw() {

        for (int i = 0; i < backgroundObjects.size(); i++) {
            BackgroundObject obj = backgroundObjects.get(i);
            if (obj instanceof Island)
                obj.draw();
        }

        for (int i = 0; i < backgroundObjects.size(); i++) {
            BackgroundObject obj = backgroundObjects.get(i);
            if (obj instanceof Cloud)
                obj.draw();
        }

    }

    public void move() {

        for (int i = 0; i < backgroundObjects.size(); i++)
            backgroundObjects.get(i).move();

    }

    public void dispose() {
        dispatchHandler.removeCallbacks(objectCreator);
    }

    class ObjectCreator implements Runnable {

        @Override
        public void run() {

            //remove out of screen planes
            //using iterator fo remove items from list during iteration - this is the only safest way
            for (Iterator<BackgroundObject> iterator = backgroundObjects.iterator(); iterator.hasNext(); ) {
                BackgroundObject backgroundObject = iterator.next();
                if (backgroundObject.isDismissed()) {
                    iterator.remove();
                }
            }

            //add new backgroundObjects
            if (backgroundObjects.size() < MAX_OBJECTS) {

                BackgroundObject backgroundObject;

                if (Tools.getRandom(0, 1) == 1)
                    backgroundObject = new Cloud(gameView);
                else
                    backgroundObject = new Island(gameView);

                backgroundObjects.add(backgroundObject);
            }

            Log.d("Number of Clouds", String.valueOf(backgroundObjects.size()));

            int maxDispatchInterval = MAX_DISPATCH_INTERVAL - 10 * gameView.enemyPlaneManager.getNumberOfPlanesDismissed();
            if (maxDispatchInterval <= 10)
                maxDispatchInterval = 10;

            dispatchHandler.postDelayed(this, Tools.getRandom(0, maxDispatchInterval));

        }
    }


}
