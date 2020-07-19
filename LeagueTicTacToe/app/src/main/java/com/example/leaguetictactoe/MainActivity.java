package com.example.leaguetictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] gameBoard = new Button[3][3];
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private boolean player1Turn = true;
    private int round;
    private int player1Score;
    private int player2Score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewPlayer1 = findViewById(R.id.text_view_player1);
        textViewPlayer2 = findViewById(R.id.text_view_player2);

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                String buttonID = "button_" + x + y ;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                gameBoard[x][y] = findViewById(resID);
                gameBoard[x][y].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (((Button) v).getText().toString().equals("")) {
            if (player1Turn) {
                ((Button) v).setText("X");
            } else {
                ((Button) v).setText("O");
            }

            round++;

            if (gameOver()) {
                if (player1Turn) {
                    player1Won();
                } else {
                    player2Won();
                }
            } else if (round == 9) {
                tieGame();
            } else {
                player1Turn = !player1Turn;
            }
        }
    }

    private boolean gameOver() {
        String[][] field = new String[3][3];
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                field[x][y] = gameBoard[x][y].getText().toString();
            }
        }
        for (int x = 0; x < 3; x++) {
            if (field[x][0].equals(field[x][1])
                    && field[x][0].equals(field[x][2])
                    && !field[x][0].equals("")) {
                return true;
            }
        }
        for (int y = 0; y < 3; y++) {
            if (field[0][y].equals(field[1][y])
                    && field[0][y].equals(field[2][y])
                    && !field[0][y].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    private void player1Won() {
        player1Score++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Won() {
        player2Score++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void tieGame() {
        Toast.makeText(this, "It's a tie!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer1.setText("Player 1: " + player1Score);
        textViewPlayer2.setText("Player 2: " + player2Score);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameBoard[i][j].setText("");
            }
        }
        round = 0;
        player1Turn = true;
    }

    private void resetGame() {
        player1Score = 0;
        player2Score = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("round", round);
        outState.putInt("player1Score", player1Score);
        outState.putInt("player2Score", player2Score);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        round = savedInstanceState.getInt("round");
        player1Score = savedInstanceState.getInt("player1Score");
        player2Score = savedInstanceState.getInt("player2Score");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}