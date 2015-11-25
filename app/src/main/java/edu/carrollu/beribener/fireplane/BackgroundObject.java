package edu.carrollu.beribener.fireplane;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by beribener on 11/12/15.
 */
public abstract class BackgroundObject extends MoveableSprite {

    public BackgroundObject(GameView gameView) {
        super(gameView);
    }

    @Override
    public void onMove() {

        //initial positionig setup
        if (this.getX() == -100)
            this.setX(Tools.getRandom(0, gameView.getCanvasWidth() - this.getWidth()));
    }

    public boolean isDismissed() {

        if (this.getX() < -getWidth() || this.getX() > gameView.getCanvasWidth() || this.getY() < -getHeight() || this.getY() > gameView.getCanvasHeight())
            return true;

        return false;

    }


}
