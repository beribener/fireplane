package edu.carrollu.beribener.fireplane;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by beribener on 11/18/15.
 */
public class FireBall extends MoveableSprite {

    private Paint paint;
    private boolean isAlive;

    public FireBall(GameView gameView){
        super(gameView);

        this.paint = new Paint();
        this.paint.setColor(Color.YELLOW);

        this.setX(gameView.playerPlane.getX() + gameView.playerPlane.getWidth() / 2);
        this.setY(gameView.playerPlane.getY());
        this.setSpeedV(gameView.moveSpeed);
        this.setDirectionV(MoveableSprite.DIRECTION_UP);

        this.isAlive = true;
    }

    @Override
    public int getWidth() {
        return 10;
    }

    @Override
    public int getHeight() {
        return 10;
    }

    public boolean isDead() {
        return !this.isAlive;
    }

    public boolean isAlive(){
        return this.isAlive;
    }

    public void destroy() {
        this.isAlive=false;
    }


    @Override
    public void draw() {

        Canvas canvas = gameView.canvas;
        canvas.drawCircle(this.getX(),this.getY(),10,this.paint);

    }

}
