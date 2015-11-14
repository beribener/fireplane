package edu.carrollu.beribener.fireplane;


import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
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

    @Override
    public Point doesColllideWith(Sprite playerPlane) {

        synchronized (this) {

            for (Iterator<EnemyPlane> iterator = enemies.iterator(); iterator.hasNext(); ) {
                EnemyPlane enemyPlane = iterator.next();

                if (enemyPlane.getY() + enemyPlane.getHeight() < playerPlane.getY())
                    continue;

                // if (playerPlane.getX() < 0 && enemyPlane.getX() < 0 && playerPlane.getY() < 0 && enemyPlane.getY() < 0)
                //   return null;

                Rect r1 = playerPlane.getBounds();

                Rect r2 = enemyPlane.getBounds();

                Rect r3 = new Rect(r1);

                //detect collision
                //http://www.techrepublic.com/blog/software-engineer/the-abcs-of-android-game-development-detect-collisions/
                if (r1.intersect(r2)) {

                    for (int i = r1.left; i < r1.right; i++) {

                        for (int j = r1.top; j < r1.bottom; j++) {

                            if (playerPlane.getBitmap().getPixel(i - r3.left, j - r3.top) !=
                                    Color.TRANSPARENT) {

                                if (enemyPlane.getBitmap().getPixel(i - r2.left, j - r2.top) !=
                                        Color.TRANSPARENT) {

                                    return new Point(enemyPlane.getX() +
                                            i - r2.left, enemyPlane.getY() + j - r2.top);
                                }
                            }
                        }
                    }
                }
            }
        }


        return null;
    }

    class EnemyPlaneCreator implements Runnable {

        @Override
        public void run() {

            synchronized (EnemyPlaneManager.this) {

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
