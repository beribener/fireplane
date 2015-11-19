package edu.carrollu.beribener.fireplane;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by beribener on 11/18/15.
 */
public class FireBallManager implements IMoveable, ICollidable {

    private ArrayList<FireBall> fireBalls;

    private GameView gameView;

    private int playerFired=0;

    public FireBallManager(GameView gameView) {
        this.gameView = gameView;
        this.fireBalls = new ArrayList<FireBall>();
    }

    public void playerFired() {
        playerFired++;
    }

    @Override
    public Point doesColllideWith(Sprite sprite) {

        for(FireBall f : this.fireBalls){

            if(f.isAlive()) {
                Point collision = f.doesColllideWith(sprite);
                if (collision != null) {
                    f.destroy();
                    return collision;
                }
            }
        }

        return null;
    }

    @Override
    public void move() {

        //add new fireballs
        while(this.playerFired>0) {
            FireBall fireBall = new FireBall(this.gameView);
            this.fireBalls.add(fireBall);
            this.playerFired--;
        }

        //remove dead fireballs from list
        for (Iterator<FireBall> iterator = fireBalls.iterator(); iterator.hasNext(); ) {
            FireBall fireBall = iterator.next();
            if(fireBall.isDead())
                iterator.remove();
        }

        for (FireBall f : this.fireBalls)
            f.move();

    }

    @Override
    public void draw() {
        for (FireBall f : this.fireBalls)
            f.draw();
    }
}
