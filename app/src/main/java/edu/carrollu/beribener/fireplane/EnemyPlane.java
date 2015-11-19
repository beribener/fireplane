package edu.carrollu.beribener.fireplane;

import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by beribener on 11/11/15.
 */
public final class EnemyPlane extends Plane {

    private int[] enemyPlaneBitmaps = new int[]{R.mipmap.bm_enemy1, R.mipmap.bm_enemy2, R.mipmap.bm_enemy3};
    private boolean isAlive;

    public EnemyPlane(GameView gameView) {
        super(gameView);

        int random = Tools.getRandom(0, 2);

        this.bitmap = BitmapFactory.decodeResource(gameView.getResources(), enemyPlaneBitmaps[random]);
        this.position = new Point(-100, -this.getHeight());

        this.setDirectionV(Plane.DIRECTION_DOWN);
        this.setSpeedV(gameView.moveSpeed);

        this.isAlive=true;
    }

    @Override
    public void onMove() {


        //initial positionig setup
        if (this.getX() == -100)
            this.setX(Tools.getRandom(0, gameView.getCanvasWidth() - this.getWidth()));
    }


    public boolean isDismissed() {

        //if the plane is out of screen
        if(this.getY() > gameView.getCanvasHeight())
            return true;
        /*
        if (this.getX() < -getWidth() || this.getX() > gameView.getCanvasWidth() || this.getY() < -getHeight() || this.getY() > gameView.getCanvasHeight())
            return true;
        */

        return false;
    }

    public boolean isDead() {
        return !this.isAlive;
    }

    public void destroy() {
        //todo show explosion and set isAlive=false;

        this.isAlive=false;
    }

}
