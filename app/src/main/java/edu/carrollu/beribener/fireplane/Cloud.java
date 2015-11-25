package edu.carrollu.beribener.fireplane;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by beribener on 11/24/15.
 */
public class Cloud extends BackgroundObject {

    private  Paint paint;

    public int[] cloudBitmaps = new int[]{R.mipmap.cloud1, R.mipmap.cloud2, R.mipmap.cloud3};


    public Cloud(GameView gameView) {

        super(gameView);

        int random = Tools.getRandom(0, cloudBitmaps.length-1);
        Bitmap bitmap = BitmapFactory.decodeResource(gameView.getResources(), cloudBitmaps[random]);

        //randomize size
        int width = Tools.getRandom(bitmap.getWidth() / 2, bitmap.getWidth());
        int height = bitmap.getHeight() * width / bitmap.getWidth();

        this.bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        this.position = new Point(-100, -this.getHeight());

        this.setDirectionV(Plane.DIRECTION_DOWN);

        random = Tools.getRandom(0,1);

        //randomize alpha
        paint = new Paint();
        if (random==0) {
            paint.setAlpha(97);
            this.setSpeedV(gameView.moveSpeed-30);
        }
        else
            this.setSpeedV(gameView.moveSpeed);

    }

    @Override
    public Paint getPaint() {

        return this.paint;
    }
}
