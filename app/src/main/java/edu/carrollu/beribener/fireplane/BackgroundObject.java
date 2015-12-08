package edu.carrollu.beribener.fireplane;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by beribener on 11/12/15.
 * Represents an island or cloud in the game.
 */
public abstract class BackgroundObject extends MoveableSprite {

    public BackgroundObject(GameView gameView) {
        super(gameView);
    }

    @Override
    public void onMove() {

        //initially position on top of the screen (outside so that it's not visible)
        if (this.getX() == -100)
            this.setX(Tools.getRandom(0, gameView.getCanvasWidth() - this.getWidth()));
    }

    public boolean isDismissed() {

        //if the object is out of the screen boundaries, that means object is dismissed - so it can be removed.
        if (this.getX() < -getWidth() || this.getX() > gameView.getCanvasWidth() || this.getY() < -getHeight() || this.getY() > gameView.getCanvasHeight())
            return true;

        return false;

    }


}
