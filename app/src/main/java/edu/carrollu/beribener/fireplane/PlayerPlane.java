package edu.carrollu.beribener.fireplane;

import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by beribener on 11/11/15.
 */
public final class PlayerPlane extends Plane {

    public PlayerPlane(GameView gameView) {
        super(gameView);
        this.bitmap = BitmapFactory.decodeResource(gameView.getResources(), R.mipmap.bm_player);
        this.position = new Point(-100,-100);

        this.setSpeedH(gameView.moveSpeed);
    }

    @Override
    public void onMove() {

        //initial player position
        if(this.getX()==-100)
            this.position = new Point(gameView.getCanvasWidth()/2-this.getWidth()/2,gameView.getCanvasHeight()-this.getHeight());

        //restrict movement in boundaries
        int maxX = gameView.getCanvasWidth()-this.getWidth();

        if(this.getX()<0)
            this.setX(0);
        else if(this.getX()>maxX)
            this.setX(maxX);

    }

}
