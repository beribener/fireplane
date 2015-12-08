package edu.carrollu.beribener.fireplane;

import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by beribener on 11/11/15.
 * Represents an enemy plane in the game.
 */
public final class EnemyPlane extends Plane {

    private int[] enemyPlaneBitmaps = new int[]{R.mipmap.bm_enemy1, R.mipmap.bm_enemy2, R.mipmap.bm_enemy3};

    private boolean isAlive;
    private boolean isExploding;
    private int explosionIndex;
    private int explosionRepeatCount;

    public EnemyPlane(GameView gameView) {
        super(gameView);

        int random = Tools.getRandom(0, 2);

        //choose random bitmap for the plane
        this.bitmap = BitmapFactory.decodeResource(gameView.getResources(), enemyPlaneBitmaps[random]);

        //set position out of the upper boundary of screen
        this.position = new Point(-100, -this.getHeight());

        //set direction & speed
        this.setDirectionV(Plane.DIRECTION_DOWN);
        this.setSpeedV(gameView.moveSpeed);

        this.isAlive = true;
        this.isExploding = false;
        this.explosionIndex = 0;
        this.explosionRepeatCount=0;
    }

    @Override
    public void onMove() {

        //initial positionig setup
        if (this.getX() == -100)
            this.setX(Tools.getRandom(0, gameView.getCanvasWidth() - this.getWidth()));
    }

    /**
     * Returns true if the plane is out of screen boundaries.
     * @return
     */
    public boolean isDismissed() {

        //if the plane is out of screen
        if (this.getY() > gameView.getCanvasHeight())
            return true;

        return false;
    }

    /**
     * Returns true if the plane is shoot down by player plane.
     * @return
     */
    public boolean isDead() {
        return !this.isAlive;
    }

    /**
     * Sets plane to exploding state.
     */
    public void destroy() {
        if(!this.isExploding) {
            this.isExploding = true;
            gameView.getSoundManager().playExplosion();
        }
    }

    @Override
    public void draw() {

        //explosions ended - mark as dead
        if (explosionRepeatCount == 2) {
            this.isAlive = false;
            return;
        }

        //set explosion bitmaps
        if (this.isExploding) {

            this.bitmap = BitmapFactory.decodeResource(gameView.getResources(), explosions[explosionIndex]);
            explosionIndex++;

            if(explosionIndex==explosions.length) {
                explosionIndex = 0;
                explosionRepeatCount++;
            }


        }

        super.draw();
    }
}
