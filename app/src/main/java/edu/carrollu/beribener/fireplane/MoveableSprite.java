package edu.carrollu.beribener.fireplane;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by beribener on 11/11/15.
 */
public abstract class MoveableSprite extends Sprite implements IMoveable {

    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_LEFT = -1;
    public static final int DIRECTION_ZERO = 0;
    public static final int DIRECTION_DOWN = DIRECTION_RIGHT;
    public static final int DIRECTION_UP = DIRECTION_LEFT;

    private int directionH = 0;
    private int directionV = 0;

    private int speedH = 0;
    private int speedV = 0;


    public MoveableSprite(GameView gameView) {
        super(gameView);

    }

    public int getDirectionH() {
        return directionH;
    }

    public void setDirectionH(int directionH) {
        this.directionH = directionH;
    }

    public int getDirectionV() {
        return directionV;
    }

    public void setDirectionV(int directionV) {
        this.directionV = directionV;
    }

    public int getSpeedV() {
        return speedV;
    }

    public void setSpeedV(int speedV) {
        this.speedV = speedV;
    }

    public int getSpeedH() {
        return speedH;
    }

    public void setSpeedH(int speedH) {
        this.speedH = speedH;
    }

    public void move() {

        if(gameView.fps == 0)
            return;

        this.onMove();

        if (directionH != DIRECTION_ZERO)
            this.setX(this.getX() + ((this.speedH / gameView.fps) * this.directionH));

        if (directionV != DIRECTION_ZERO)
            this.setY(this.getY() + ((this.speedV / gameView.fps) * this.directionV));

    }

    public void onMove() {

    }




}
