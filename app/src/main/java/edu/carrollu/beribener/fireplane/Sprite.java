package edu.carrollu.beribener.fireplane;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by beribener on 11/11/15.
 */
public abstract class Sprite implements IDrawable,IMoveable {

    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_LEFT = -1;
    public static final int DIRECTION_ZERO = 0;
    public static final int DIRECTION_DOWN = DIRECTION_RIGHT;

    private int directionH = 0;
    private int directionV = 0;

    private int speedH = 0;
    private int speedV = 0;

    protected GameView gameView;
    protected Bitmap bitmap;

    protected Point position;

    public Sprite() {

    }

    public Sprite(GameView gameView) {

        this.gameView = gameView;
        this.position = new Point(0, 0);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getWidth() {
        return this.bitmap.getWidth();
    }

    public int getHeight() {
        return this.bitmap.getHeight();
    }

    public Point getPosition() {
        return position;
    }

    public int getX() {
        return this.position.x;
    }

    public void setX(int x) {
        this.position.x = x;
    }

    public int getY() {
        return this.position.y;
    }

    public void setY(int y) {
        this.position.y = y;
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

        if (directionH != DIRECTION_ZERO)
            this.setX(this.getX() + ((this.speedH / gameView.fps) * this.directionH));

        if (directionV != DIRECTION_ZERO)
            this.setY(this.getY() + ((this.speedV / gameView.fps) * this.directionV));

    }

    public void draw() {

        this.onDraw();

        Canvas canvas = this.gameView.canvas;
        Paint paint = this.gameView.paint;
        canvas.drawBitmap(this.bitmap, this.getX(), this.getY(), paint);


    }

    protected void onDraw() {

    }


}
