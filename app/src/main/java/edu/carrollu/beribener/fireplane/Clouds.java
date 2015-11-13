package edu.carrollu.beribener.fireplane;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by beribener on 11/12/15.
 */
public class Clouds extends MoveableSprite {

    public final int CLOUDS_MOVE_SPEED = 50;

    public Clouds(GameView gameView){
        super(gameView);

        this.bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(gameView.getResources(), R.mipmap.bm_clouds),1080,3840,false);
        this.position = new Point(0, -this.getHeight());

        this.setDirectionV(Plane.DIRECTION_DOWN);
        this.setSpeedV(200);
    }


}
