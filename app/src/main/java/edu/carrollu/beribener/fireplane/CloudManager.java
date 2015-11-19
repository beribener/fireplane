package edu.carrollu.beribener.fireplane;


import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by beribener on 11/11/15.
 */
public class CloudManager implements IMoveable {

    private GameView gameView;
    private ArrayList<Cloud> clouds;

    private final int MAX_CLOUDS = 10;
    private final int MAX_DISPATCH_INTERVAL = 1000;

    //handler for dispatching of clouds
    private Handler dispatchHandler;
    private Runnable cloudCreator;

    public CloudManager(GameView gameView) {

        this.gameView = gameView;
        clouds = new ArrayList<Cloud>();

        cloudCreator = new CloudCreator();

        dispatchHandler = new Handler();
        dispatchHandler.postDelayed(cloudCreator, MAX_DISPATCH_INTERVAL);

    }

    public void draw() {

        for (int i = 0; i < clouds.size(); i++)
            clouds.get(i).draw();

    }

    public void move() {

        for (int i = 0; i < clouds.size(); i++) {
            clouds.get(i).move();
        }
    }

    class CloudCreator implements Runnable {

        @Override
        public void run() {

            //remove out of screen planes
            //using iterator fo remove items from list during iteration - this is the only safest way
            for (Iterator<Cloud> iterator = clouds.iterator(); iterator.hasNext(); ) {
                Cloud cloud = iterator.next();
                if (cloud.isDismissed()) {
                    iterator.remove();
                }
            }

            //add new planes
            if (clouds.size() < MAX_CLOUDS) {

                int speed = Cloud.MOVE_SPEED_SLOW;
                int random = Tools.getRandom(1, 2);
                if (random % 2 == 0)
                    speed = Cloud.MOVE_SPEED_FAST;

                //int speed = Tools.getRandom(70,Cloud.MOVE_SPEED_FAST);

                Cloud cloud = new Cloud(gameView, speed);
                clouds.add(cloud);

            }

            Log.d("Number of Clouds", String.valueOf(clouds.size()));

            int maxDispatchInterval = MAX_DISPATCH_INTERVAL - 10 * gameView.enemyPlaneManager.getNumberOfPlanesDismissed();
            if (maxDispatchInterval <= 10)
                maxDispatchInterval = 10;

            dispatchHandler.postDelayed(this, Tools.getRandom(0,maxDispatchInterval ));

        }
    }


}
