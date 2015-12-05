package edu.carrollu.beribener.fireplane;

import android.media.MediaPlayer;
import android.media.SoundPool;

import java.io.IOException;

/**
 * Created by beribener on 12/1/15.
 */
public class SoundManager {

    private GameView gameView;
    private MediaPlayer firePlayer;

    private MediaPlayer musicPlayer;

    private MediaPlayer explosionPlayer;


    public SoundManager(GameView gameview) {
        this.gameView = gameview;
        this.firePlayer = MediaPlayer.create(this.gameView.getContext(), R.raw.fire);
        this.musicPlayer = MediaPlayer.create(this.gameView.getContext(), R.raw.bg2);
        this.musicPlayer.setLooping(true);
        this.musicPlayer.setVolume(0.1F, 0.1F);

        this.explosionPlayer = MediaPlayer.create(this.gameView.getContext(), R.raw.explosion);
    }


    public void playFireSound() {

        try {
            if (firePlayer.isPlaying()) {
                firePlayer.stop();
                firePlayer.prepare();

            }
        } catch (Exception ex) {
        }
        firePlayer.start();

    }

    public void playMusic() {

        musicPlayer.start();

    }

    public void playExplosion() {

        if (explosionPlayer.isPlaying()) {
            explosionPlayer.stop();
            try {
                explosionPlayer.prepare();
            } catch (Exception ex){

            }
        }

        explosionPlayer.start();

    }

    public void dispose(){
        this.firePlayer.release();
        this.explosionPlayer.release();
        this.musicPlayer.release();
    }

}
