package edu.carrollu.beribener.fireplane;

/**
 * Created by beribener on 12/2/15.
 */
public class HighScore implements Comparable<HighScore> {

    private String name;
    private int score;

    public HighScore(String rawString){

        String[] arr = rawString.split("_");
        this.name = arr[0];
        this.score=Integer.parseInt(arr[1]);

    }

    public HighScore(String name,int score){
        this.name = name;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s_%s",this.name,this.score);
    }

    @Override
    public int compareTo(HighScore another) {

        if(this.score<another.score)
            return 1;

        if(this.score>another.score)
            return -1;

        //do alphabetic comparison
        return this.name.compareTo(another.name);

    }
}
