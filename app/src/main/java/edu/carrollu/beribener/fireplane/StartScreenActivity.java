package edu.carrollu.beribener.fireplane;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class StartScreenActivity extends AppCompatActivity {

    ImageButton startButton;
    ImageButton highScoreButton;
    ImageButton exitButton;
    TextView scoreText;
    int score = 0;
    HighscoreManager highscoreManager;

    EditText txtName;
    ImageButton btnAddScore;

    final int GAME_INTENT_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start_screen);

        this.highscoreManager = new HighscoreManager(this);
        //highscoreManager.clearScores();

        startButton = (ImageButton) findViewById(R.id.btnStart);
        highScoreButton = (ImageButton) findViewById(R.id.btnStart);
        exitButton = (ImageButton) findViewById(R.id.btnStart);
        scoreText = (TextView) findViewById(R.id.txtScore);

        txtName = (EditText) findViewById(R.id.txtName);
        btnAddScore = (ImageButton) findViewById(R.id.btnAddScore);

        this.hideHighScoreInput();
    }

    public void onStartButtonClick(View view) {
        this.hideHighScoreInput();
        Intent game = new Intent(this, GameEngine.class);
        startActivityForResult(game, GAME_INTENT_ID);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GAME_INTENT_ID) {

            score = 0;

            if (resultCode == RESULT_OK) {
                score = data.getIntExtra("score", 0);
                scoreText.setText(String.format("Your score was: %s", score));

                if (score > 0) {
                    this.showHighScoreInput();
                }

            }
        }
    }

    public void onAddScoreButtonClick(View v) {

        String name = txtName.getText().toString().trim();

        if (name.length() == 0) {
            Toast toast = Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        this.highscoreManager.addScore(name, score);
        txtName.setText("");
        this.hideHighScoreInput();
        this.showHighScores();

        Log.d("SCORES", this.highscoreManager.toString());
    }

    public void onHighScoreButtonClick(View view) {
        this.showHighScores();

    }

    private void showHighScores() {
        Intent highScores = new Intent(this, HighScoresActivity.class);
        startActivity(highScores);
    }


    public void onExitButtonClick(View view) {
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void hideHighScoreInput() {
        txtName.setVisibility(View.INVISIBLE);
        btnAddScore.setVisibility(View.INVISIBLE);
    }

    private void showHighScoreInput() {
        txtName.setVisibility(View.VISIBLE);
        btnAddScore.setVisibility(View.VISIBLE);
    }
}
