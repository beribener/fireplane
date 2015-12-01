package edu.carrollu.beribener.fireplane;

/**
 * Created by beribener on 11/11/15.
 */
public abstract class Plane extends MoveableSprite {


    protected int[] explosions = new int[]{R.mipmap.explosion1,
            R.mipmap.explosion2,
            R.mipmap.explosion3,
            R.mipmap.explosion4,
            R.mipmap.explosion5

    };
    public Plane(GameView gameView) {
        super(gameView);
    }



}
