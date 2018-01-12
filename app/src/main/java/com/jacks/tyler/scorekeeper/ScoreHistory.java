package com.jacks.tyler.scorekeeper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class ScoreHistory extends Activity {

    String scoreSetCount = "0";
    ArrayList<String> scores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_history_layout);

        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        scoreSetCount = prefs.getString("scoreSetCount", "0");
        getListOfScores();

        // have a list view and display all the scores in the format of:
        // Week <scoreSetCount>
        //      <playerOneName>: <playerOneScore>
        //      <playerTwoMame>: <playerTwoScore>
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.score_history_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getListOfScores() {
        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        for (int i = 1; i <= Integer.parseInt(scoreSetCount); i++) {
            scores.add(prefs.getString(Integer.toString(i), "N/A"));
        }
    }
}
