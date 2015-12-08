package edu.carrollu.beribener.fireplane;

import java.util.Random;

/**
 * Class for helper methods.
 * Created by beribener on 11/11/15.
 */
public class Tools {

    static int getRandom(int upperBound){
        return getRandom(0,upperBound);
    }

    static int getRandom(int lowerBound,int upperBound){
        // initialize a random
        Random random = new Random();

        if(lowerBound==0)
            upperBound++;

        return random.nextInt(upperBound)+lowerBound;
    }


}
