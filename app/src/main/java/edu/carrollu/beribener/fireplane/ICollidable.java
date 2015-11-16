package edu.carrollu.beribener.fireplane;

import android.graphics.Point;

/**
 * Created by beribener on 11/14/15.
 */
public interface ICollidable extends IDrawable {
    Point doesColllideWith(Sprite sprite);
}
