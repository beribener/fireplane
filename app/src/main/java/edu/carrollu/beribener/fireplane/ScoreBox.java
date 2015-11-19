package edu.carrollu.beribener.fireplane;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;

/**
 * Created by beribener on 11/13/15.
 */
public class ScoreBox extends Sprite {

    Paint paint;
    int i=0;

    public ScoreBox(GameView gameView)
    {
        super(gameView);


        paint = new Paint();
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setTextSize(100);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setTextAlign(Paint.Align.RIGHT);
    }

    @Override
    public void draw() {
        //gameView.canvas.drawText("Hello world " + i++,this.getX(),this.getY(),this.paint);
        this.position = new Point(gameView.getCanvasWidth(),80);
        gameView.canvas.drawText(gameView.enemyPlaneManager.getNumberOfPlanesDismissed()+"",this.getX(),this.getY(),this.paint);
    }
}
