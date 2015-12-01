package edu.carrollu.beribener.fireplane;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by beribener on 11/11/15.
 */
public final class PlayerPlane extends Plane {

    private int explosionIndex;
    private int explosionRepeatCount;
    private boolean isExploding;

    public PlayerPlane(GameView gameView) {
        super(gameView);

        this.bitmap = BitmapFactory.decodeResource(gameView.getResources(), R.mipmap.bm_player);
        this.position = new Point(-100, -100);

        this.isExploding = false;
        this.explosionIndex = 0;
        this.explosionRepeatCount=0;
    }




    @Override
    public void onMove() {

        if(this.isExploding)
            return;

        this.setSpeedH(gameView.moveSpeed);

        //initial player position
        if (this.getX() == -100)
            this.position = new Point(gameView.getCanvasWidth() / 2 - this.getWidth() / 2, gameView.getCanvasHeight() - this.getHeight());

        //restrict movement in boundaries
        int maxX = gameView.getCanvasWidth() - this.getWidth();

        if (this.getX() < 0)
            this.setX(0);
        else if (this.getX() > maxX)
            this.setX(maxX);


    }

    public void destroy() {
        this.isExploding = true;
    }

    @Override
    public void draw() {

        //explosions ended
        if (explosionRepeatCount == 5) {
            gameView.onGameOver();
            return;
        }

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
