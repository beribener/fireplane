package edu.carrollu.beribener.fireplane;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by beribener on 11/13/15.
 */
public class ExampleGameItem extends Sprite {

    Paint paint;
    int i=0;

    public ExampleGameItem(GameView gameView)
    {
        super(gameView);
        this.position = new Point(300,300);

        paint = new Paint();
        paint.setColor(Color.argb(255, 0, 0, 0));
        paint.setTextSize(45);
    }

    @Override
    public void draw() {
        gameView.canvas.drawText("Hello world " + i++,this.getX(),this.getY(),this.paint);
    }
}
