package edu.carrollu.beribener.fireplane;

import java.util.Random;

/**
 * Created by beribener on 11/11/15.
 */
public class Tools {

    static int getRandom(int upperBound){
        return getRandom(0,upperBound);
    }

    static int getRandom(int lowerBound,int upperBound){
        // initialize a random
        Random random = new Random();
        return random.nextInt(upperBound+1)+lowerBound;
    }


}
