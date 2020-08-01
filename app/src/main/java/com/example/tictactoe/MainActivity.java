package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    // Represents the internal state of the game
    private TicTacToeGame mGame;
    private SoundPlayer sound;
    private ImageView mImage;
    int win_count = 0;
    int tie_count = 0;
    int lose_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sound = new SoundPlayer(this);

        mGame = new TicTacToeGame();
        mBoardButtons = new Button[mGame.BOARD_SIZE];
        mBoardButtons[0] = (Button) findViewById(R.id.button0);
        mBoardButtons[1] = (Button) findViewById(R.id.button1);
        mBoardButtons[2] = (Button) findViewById(R.id.button2);
        mBoardButtons[3] = (Button) findViewById(R.id.button3);
        mBoardButtons[4] = (Button) findViewById(R.id.button4);
        mBoardButtons[5] = (Button) findViewById(R.id.button5);
        mBoardButtons[6] = (Button) findViewById(R.id.button6);
        mBoardButtons[7] = (Button) findViewById(R.id.button7);
        mBoardButtons[8] = (Button) findViewById(R.id.button8);
        mInfoTextView = (TextView) findViewById(R.id.information);
        mImage = (ImageView) findViewById(R.id.report_image);
        mGame = new TicTacToeGame();
        startNewGame();
    }
    // Buttons making up the board
    private Button mBoardButtons[];
    // Various text displayed
    private TextView mInfoTextView;
    // Restart Button
    private Button startButton;
    // Game Over
    private TextView result_win;
    private TextView result_lose;
    private TextView result_tie;
    Boolean mGameOver;
    //--- Set up the game board.
    private void startNewGame() {
        mGameOver = false;
        mGame.clearBoard();
        //---Reset all buttons
        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }
        //---Human goes first
        mInfoTextView.setText(R.string.txt1);
    }

    //---Handles clicks on the game board buttons
    private class ButtonClickListener implements View.OnClickListener {
        int location;
        DecimalFormat nf = new DecimalFormat("0");
        public ButtonClickListener(int location) {
            this.location = location;
        }
        @Override
        public void onClick(View v) {
            if (mGameOver == false) {
                if (mBoardButtons[location].isEnabled()) {
                    setMove(TicTacToeGame.HUMAN_PLAYER, location);
                    mImage.setImageResource(R.drawable.blank);
                    //--- If no winner yet, let the computer make a move
                    int winner = mGame.checkForWinner();
                    if (winner == 0) {
                        mInfoTextView.setText(R.string.txt2);
                        int move = mGame.getComputerMove();
                        setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                        winner = mGame.checkForWinner();
                        mImage.setImageResource(R.drawable.blank);
                    }
                    if (winner == 0) {
                        mInfoTextView.setTextColor(Color.rgb(0, 0, 0));
                        mInfoTextView.setText(R.string.txt3);
                        mImage.setImageResource(R.drawable.blank);
                    } else if (winner == 1) {
                        mImage.setImageResource(R.drawable.blank);
                        mImage.animate().scaleX(1.5f).scaleY(1.5f).setDuration(4000);
                        mInfoTextView.setTextColor(Color.rgb(0, 0, 200));
                        mInfoTextView.setText(R.string.txt4);
                        sound.playdrawSound();
                        tie_count++;
                        result_tie = (TextView) findViewById(R.id.tie_result);
                        result_tie.setText(getString(R.string.Tie) + " " + nf.format(tie_count));
                        mGameOver = true;
                    } else if (winner == 2) {
                        mImage.setImageResource(R.drawable.win);
                        mImage.animate().scaleX(1.5f).scaleY(1.5f).setDuration(4000);
                        mInfoTextView.setTextColor(Color.rgb(0, 200, 0));
                        mInfoTextView.setText(R.string.txt5);
                        sound.playwinSound();
                        win_count++;
                        result_win = (TextView) findViewById(R.id.win_result);
                        result_win.setText(getString(R.string.Win) + " " + nf.format(win_count));
                        mGameOver = true;
                    } else {
                        mImage.setImageResource(R.drawable.lose);
                        mImage.animate().scaleX(1.5f).scaleY(1.5f).setDuration(4000);
                        mInfoTextView.setTextColor(Color.rgb(200, 0, 0));
                        mInfoTextView.setText(R.string.txt6);
                        sound.playloseSound();
                        lose_count++;
                        result_lose = (TextView) findViewById(R.id.lose_result);
                        result_lose.setText(getString(R.string.Lose) + " " + nf.format(lose_count));
                        mGameOver = true;
                    }
                }
            }
        }
    }
    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == TicTacToeGame.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
        else
            mBoardButtons[location].setTextColor(Color.rgb(255, 255, 0));
    }
    //--- OnClickListener for Restart a New Game Button
    public void newGame(View v){

        DecimalFormat nf = new DecimalFormat("0");
        startNewGame();
        mImage.setImageResource(R.drawable.blank);
        mImage.animate().scaleX(1f).scaleY(1f).setDuration(0);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the aaction bar if it is present
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.normal:
                Toast.makeText(this, "",
                        Toast.LENGTH_LONG).show();
                return true;
            case R.id.easy:
                Toast.makeText(this, "",
                        Toast.LENGTH_LONG).show();
                return true;
            case R.id.hard:
                Toast.makeText(this, "",
                        Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_exit:
                finish();
                return true;
        }
        return false;}

}

