package edu.carrollu.beribener.fireplane;


import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by beribener on 11/11/15.
 */
public class EnemyPlaneManager implements IDrawable, IMoveable {

    private GameView gameView;
    private ArrayList<EnemyPlane> enemies;

    private final int MAX_ENEMIES = 10;
    private final int MAX_DISPATCH_INTERVAL = 5000;

    //handler for dispatch of enemyplanes
    private Handler dispatchHandler;
    private Runnable enemyPlaneCreator;

    public EnemyPlaneManager(GameView gameView) {

        this.gameView = gameView;
        enemies = new ArrayList<EnemyPlane>();

        enemyPlaneCreator = new EnemyPlaneCreator();

        dispatchHandler = new Handler();
        dispatchHandler.postDelayed(enemyPlaneCreator, MAX_DISPATCH_INTERVAL);

    }

    public void draw() {

        for (int i = 0; i < enemies.size(); i++)
            enemies.get(i).draw();

    }

    public void move() {

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).move();
        }
    }

    class EnemyPlaneCreator implements Runnable {

        @Override
        public void run() {

            //remove out of screen planes
            //using iterator fo remove items from list during iteration - this is the only safest way
            for (Iterator<EnemyPlane> iterator = enemies.iterator(); iterator.hasNext(); ) {
                EnemyPlane enemyPlane = iterator.next();
                if (enemyPlane.isDismissed()) {
                    iterator.remove();
                }
            }

            //add new planes
            if (enemies.size() < MAX_ENEMIES) {

                EnemyPlane enemy = new EnemyPlane(gameView);
                enemies.add(enemy);

            }

            /*for (int i = 0; i < enemies.size(); i++) {
                EnemyPlane enemyPlane = enemies.get(i);

                //int directionH = Tools.getRandom(0, 2) % 2 == 1 ? Plane.DIRECTION_RIGHT : Plane.DIRECTION_LEFT;
                //enemyPlane.setDirectionH(directionH);

                //int speedH = Tools.getRandom(50, gameView.moveSpeed);
                //enemyPlane.setSpeedH(gameView.moveSpeed);
            }*/

            Log.d("Number of EnemyPlanes", String.valueOf(enemies.size()));

            dispatchHandler.postDelayed(this, Tools.getRandom(1000, MAX_DISPATCH_INTERVAL));

        }
    }


}
