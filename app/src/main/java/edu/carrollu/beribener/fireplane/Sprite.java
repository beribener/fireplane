package edu.carrollu.beribener.fireplane;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by beribener on 11/13/15.
 */
public abstract class Sprite implements IDrawable {

    protected GameView gameView;
    protected Bitmap bitmap;
    protected Point position;

    public Sprite(GameView gameView){
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

    public void draw() {

        Canvas canvas = this.gameView.canvas;
        Paint paint = this.getPaint();
        canvas.drawBitmap(this.bitmap, this.getX(), this.getY(), paint);

    }

    public Paint getPaint() {
        return this.gameView.paint;
    }


}
