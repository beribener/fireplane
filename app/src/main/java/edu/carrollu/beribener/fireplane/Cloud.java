package edu.carrollu.beribener.fireplane;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by beribener on 11/12/15.
 */
public class Cloud extends MoveableSprite {

    public static final int MOVE_SPEED_FAST = 120;
    public static final int MOVE_SPEED_SLOW = 80;

    public int[] cloudBitmaps = new int[]{R.mipmap.cloud1, R.mipmap.cloud2, R.mipmap.cloud3};

    Paint paint;

    public Cloud(GameView gameView, int speed) {
        super(gameView);

        int random = Tools.getRandom(0, 2);
        Bitmap bitmap = BitmapFactory.decodeResource(gameView.getResources(), cloudBitmaps[random]);

        //randomize size
        int width = Tools.getRandom(bitmap.getWidth() / 2, bitmap.getWidth());
        int height = bitmap.getHeight() * width / bitmap.getWidth();

        this.bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        this.position = new Point(-100, -this.getHeight());

        this.setDirectionV(Plane.DIRECTION_DOWN);
        this.setSpeedV(speed);

        //randomize alpha
        paint = new Paint();
        if (Tools.getRandom(1, 2) % 2 == 0) {
            int alpha = Tools.getRandom(95, 100);
            paint.setAlpha(alpha);
        }
    }

    @Override
    public Paint getPaint() {

        return this.paint;
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
