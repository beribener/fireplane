package edu.carrollu.beribener.fireplane;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by beribener on 11/13/15.
 */
public abstract class Sprite implements IDrawable, ICollidable {

    protected GameView gameView;
    protected Bitmap bitmap;
    protected Point position;

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

    public Rect getBounds() {
        return new Rect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight());

    }

    public void draw() {

        Canvas canvas = this.gameView.canvas;
        Paint paint = this.getPaint();
        canvas.drawBitmap(this.bitmap, this.getX(), this.getY(), paint);

    }

    public Paint getPaint() {
        return this.gameView.paint;
    }

    @Override
    public Point doesColllideWith(Sprite sprite) {
        Rect r1 = this.getBounds();

        Rect r2 = sprite.getBounds();

        Rect r3 = new Rect(r1);

        //detect collision
        //http://www.techrepublic.com/blog/software-engineer/the-abcs-of-android-game-development-detect-collisions/
        if (r1.intersect(r2)) {

            for (int i = r1.left; i < r1.right; i++) {

                for (int j = r1.top; j < r1.bottom; j++) {

                    if (this.getBitmap().getPixel(i - r3.left, j - r3.top) !=
                            Color.TRANSPARENT) {

                        if (sprite.getBitmap().getPixel(i - r2.left, j - r2.top) !=
                                Color.TRANSPARENT) {

                            return new Point(sprite.getX() +
                                    i - r2.left, sprite.getY() + j - r2.top);
                        }
                    }
                }
            }
        }


        return null;
    }
}
