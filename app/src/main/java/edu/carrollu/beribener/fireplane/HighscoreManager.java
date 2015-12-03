package edu.carrollu.beribener.fireplane;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by beribener on 12/2/15.
 */
public class HighscoreManager {

    Context context;
    SharedPreferences prefs;
    private static HighscoreManager instance;

    public HighscoreManager(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences("HIGHSCORES", Context.MODE_PRIVATE);
        instance = this;
    }

    public static HighscoreManager instance() {
        return HighscoreManager.instance;
    }

    public ArrayList<HighScore> getScores() {

        Set<String> scores = this.prefs.getStringSet("HIGHSCORES", null);

        ArrayList<HighScore> result = new ArrayList<HighScore>();

        if (scores != null)
            for (String score : scores) {
                result.add(new HighScore(score));
            }

        Collections.sort(result);

        return result;
    }

    public String[] getScoresAsStringArray()

    {
        ArrayList<HighScore> scores = this.getScores();
        String[] result = new String[scores.size()];
        int i = 0;

        for (HighScore score : scores
                ) {
            result[i++] = String.format("%s - %s", score.getScore(), score.getName());

        }

        return result;

    }

    public void addScore(String name, int score) {


        ArrayList<HighScore> scores = this.getScores();

        //add new score
        HighScore newScore = new HighScore(name, score);

        scores.add(newScore);

        Set<String> newScores = new HashSet<String>();

        for (int i = 0; i < 10 && i < scores.size(); i++) {
            newScores.add(scores.get(i).toString());
        }

        Editor editor = this.prefs.edit();
        editor.putStringSet("HIGHSCORES", newScores);
        editor.commit();

    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        ArrayList<HighScore> scores = this.getScores();

        for (HighScore score : scores) {
            sb.append(score.toString());
            sb.append("\n");
        }

        return sb.toString();


    }
}
