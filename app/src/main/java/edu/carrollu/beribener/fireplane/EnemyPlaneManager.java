package edu.carrollu.beribener.fireplane;


import android.graphics.Point;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by beribener on 11/11/15.
 */
public class EnemyPlaneManager implements IMoveable, ICollidable {

    private GameView gameView;
    private ArrayList<EnemyPlane> enemies;

    private final int MAX_ENEMIES = 10;
    private final int START_DISPATCH_INTERVAL = 1000;

    //handler for dispatching of enemyplanes
   /* private Handler dispatchHandler;
    private Runnable enemyPlaneCreator;*/

    private int numberOfPlanesDismissed;

    public EnemyPlaneManager(GameView gameView) {

        this.gameView = gameView;
        enemies = new ArrayList<EnemyPlane>();
        this.numberOfPlanesDismissed=0;

        /*enemyPlaneCreator = new EnemyPlaneCreator();

        dispatchHandler = new Handler();
        dispatchHandler.postDelayed(enemyPlaneCreator, START_DISPATCH_INTERVAL);*/

    }

    public int getNumberOfPlanesDismissed() {
        return numberOfPlanesDismissed;
    }

    public void draw() {

        for (int i = 0; i < enemies.size(); i++)
            enemies.get(i).draw();

    }

    public void move() {

        this.createPlanes();

        //remove out of screen and dead planes
        //using iterator fo remove items from list during iteration - this is the only safest way
        for (Iterator<EnemyPlane> iterator = enemies.iterator(); iterator.hasNext(); ) {
            EnemyPlane enemyPlane = iterator.next();

            //check if enemyplane is shot
            Point shotPoint = gameView.fireBallManager.doesColllideWith(enemyPlane);
            if(shotPoint!=null)
                enemyPlane.destroy();

            //remove dead planes from list
            if (enemyPlane.isDismissed() || enemyPlane.isDead()) {
                this.numberOfPlanesDismissed++;
                iterator.remove();
            }
        }


        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).move();
        }

    }

    @Override
    public Point doesColllideWith(Sprite sprite) {


        for (int i = 0; i < enemies.size(); i++) {
            EnemyPlane enemyPlane = enemies.get(i);

            //check if our plane collides with living planes
            if (!enemyPlane.isDead()) {
                Point collision = sprite.doesColllideWith(enemyPlane);
                if (collision != null) {
                    enemyPlane.destroy();
                    return collision;
                }
            }

        }

        return null;
    }


    public void createPlanes() {

        int dispatchInterval = 200 - numberOfPlanesDismissed*10;
        if(dispatchInterval<=0)
            dispatchInterval=20;

        if(gameView.gameCounter%dispatchInterval!=0)
            return;

        //add new planes
        if (enemies.size() < MAX_ENEMIES) {

            EnemyPlane enemy = new EnemyPlane(gameView);
            enemies.add(enemy);
        }

        Log.d("Number of EnemyPlanes", String.valueOf(enemies.size()));

       /* int timeDiff=  EnemyPlaneManager.this.getNumberOfPlanesDismissed()*10;
        int maxDispatchInterval = START_DISPATCH_INTERVAL - timeDiff;
        if(maxDispatchInterval<500)
            maxDispatchInterval=500;

        int minDispatchInterval = 500 - timeDiff;
        if(minDispatchInterval<0)
            minDispatchInterval=0;

        dispatchHandler.postDelayed(this, Tools.getRandom(minDispatchInterval, maxDispatchInterval));*/

    }


}
