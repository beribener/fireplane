package edu.carrollu.beribener.fireplane;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by beribener on 11/24/15.
 */
public class Island extends BackgroundObject {

    public int[] islandBitmaps = new int[]{R.mipmap.island1,
            R.mipmap.island2,
            R.mipmap.island3,
            R.mipmap.island4};

    public Island(GameView gameView) {
        super(gameView);

        int random = Tools.getRandom(0, islandBitmaps.length-1);
        Bitmap bitmap = BitmapFactory.decodeResource(gameView.getResources(), islandBitmaps[random]);

        //randomize size
        int width = Tools.getRandom(bitmap.getWidth() / 2, bitmap.getWidth());
        int height = bitmap.getHeight() * width / bitmap.getWidth();



        this.bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        this.position = new Point(-100, -this.getHeight());

        this.setDirectionV(Plane.DIRECTION_DOWN);

        random = Tools.getRandom(0,1);

        //randomize alpha
        this.setSpeedV(gameView.moveSpeed);
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

}
