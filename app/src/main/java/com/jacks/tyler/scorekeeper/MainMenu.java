package com.jacks.tyler.scorekeeper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import utils.StringUtils;

public class MainMenu extends Activity {

    TextView player_one;
    TextView player_two;
    TextView player_one_score;
    TextView player_two_score;
    EditText player_name_dialog_layout_edit_text;
    Button save_score;
    Button reset_score;
    Button score_history;

    String scoreSetCount = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_layout);

        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        scoreSetCount = prefs.getString("scoreSetCount", "0");

        player_one = (TextView) findViewById(R.id.player_one_text_view);
        player_two = (TextView) findViewById(R.id.player_two_text_view);
        player_one_score = (TextView) findViewById(R.id.player_one_score_text_view);
        player_two_score = (TextView) findViewById(R.id.player_two_score_text_view);
        player_name_dialog_layout_edit_text = (EditText) findViewById(R.id.player_name_dialog_layout_edit_text);
        save_score = (Button) findViewById(R.id.save_score_button);
        reset_score = (Button) findViewById(R.id.reset_score_button);
        score_history = (Button) findViewById(R.id.score_history_button);

        setOnLongClickListenerForPlayer(player_one, getResources().getString(R.string.prefs_player_one_name));
        setOnLongClickListenerForPlayer(player_two, getResources().getString(R.string.prefs_player_two_name));
        setOnClickListenerForPlayerScore(player_one_score);
        setOnClickListenerForPlayerScore(player_two_score);
        setOnLongClickListenerForPlayerScore(player_one_score);
        setOnLongClickListenerForPlayerScore(player_two_score);

        setOnClickForSaveScore(save_score);
        setOnClickForResetScore(reset_score);
        setOnClickForScoreHistory(score_history, new Intent(this, ScoreHistory.class));

        tryToSetPlayerNames(player_one, player_two);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_menu, menu);
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

    private void setOnLongClickListenerForPlayer(final TextView view, final String playerNumber) {
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext(), AlertDialog.BUTTON_NEUTRAL);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.player_name_dialog_layout, null);
                dialogBuilder.setView(dialogView);
                player_name_dialog_layout_edit_text = (EditText) dialogView.findViewById(R.id.player_name_dialog_layout_edit_text);
                dialogBuilder.setMessage("Enter new Player Name");
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { }
                });
                dialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
                        final SharedPreferences.Editor editor = prefs.edit();
                        String playerName = player_name_dialog_layout_edit_text.getText().toString();
                        editor.putString(playerNumber, playerName);
                        editor.apply();
                        view.setText(playerName);
                    }
                });
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
                return true;
            }
        });
    }

    private void setOnClickListenerForPlayerScore(final TextView view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add 1 to the score
                view.setText(StringUtils.addOneToString(view.getText().toString()));
            }
        });
    }

    private void setOnLongClickListenerForPlayerScore(final TextView view) {
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // subtract 1 from the score (unless score value is 0, then do nothing)
                String currentScore = view.getText().toString();
                view.setText("0".equals(currentScore) ? "0" : Integer.toString(Integer.parseInt(currentScore) - 1));
                return true;
            }
        });
    }

    private void setOnClickForSaveScore(final Button save_score) {
        save_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
                SharedPreferences.Editor editor = prefs.edit();

                scoreSetCount = StringUtils.addOneToString(scoreSetCount);
                editor.putString("scoreSetCount", scoreSetCount);
                editor.putString(scoreSetCount, player_one.getText().toString() + "_" + player_two.getText().toString() +
                        "_" + player_one_score.getText().toString() + "_" + player_two_score.getText().toString());
                editor.apply();
                Toast.makeText(MainMenu.this, "Score Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setOnClickForResetScore(final Button reset_score) {
        reset_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player_one_score.setText("0");
                player_two_score.setText("0");
            }
        });
    }

    private void setOnClickForScoreHistory(final Button score_history, final Intent intent) {
        score_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }

    private void tryToSetPlayerNames(TextView player1, TextView player2) {
        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        player1.setText(prefs.getString(getResources().getString(R.string.prefs_player_one_name), "Player 1"));
        player2.setText(prefs.getString(getResources().getString(R.string.prefs_player_two_name), "Player 2"));
    }
}
