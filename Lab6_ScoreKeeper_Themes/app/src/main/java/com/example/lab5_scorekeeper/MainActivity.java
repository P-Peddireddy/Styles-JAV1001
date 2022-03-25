package com.example.lab5_scorekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private TextView tvTeamA_Score, tvTeamB_Score;
    private Button btnPlus, btnMinus, btnWinner;
    private Spinner spnrGameScore;
    private RadioGroup radioTeamGroup, radioGameGroup;
    private RadioButton radioTeamButton, radioGameButton, radioPreviousGameButton;
    Integer[] spinnerScores = {1, 2, 3, 4, 5, 6};
    private int teamAScore = 0, teamBScore = 0;
    int spinnerValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTeamA_Score = findViewById(R.id.tv_team_a_score);
        tvTeamB_Score = findViewById(R.id.tv_team_b_score);
        btnPlus = findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(this);
        btnMinus = findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(this);
        btnWinner = findViewById(R.id.btn_winner);
        btnWinner.setOnClickListener(this);
        radioTeamGroup = findViewById(R.id.radioTeamGroup);
        radioTeamButton = radioTeamGroup.findViewById(radioTeamGroup.getCheckedRadioButtonId());
        radioGameGroup = findViewById(R.id.radioGameGroup);
        radioGameButton = radioGameGroup.findViewById(radioGameGroup.getCheckedRadioButtonId());
        spnrGameScore = findViewById(R.id.spinner_game_scores);

        ArrayAdapter scoreAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerScores);
        scoreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrGameScore.setAdapter(scoreAdapter);
        spnrGameScore.setOnItemSelectedListener(this);

        resetScores();

        radioTeamGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioTeamButton = radioTeamGroup.findViewById(i);
            }
        });

        radioGameGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Do you want play new game?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        radioGameButton = radioTeamGroup.findViewById(i);
                        resetScores();
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlus:
                if (radioTeamButton.getText().equals("Team A")) {
                    teamAScore = spinnerValue + teamAScore;
                    tvTeamA_Score.setText(String.valueOf(teamAScore));
                } else {
                    teamBScore = spinnerValue + teamBScore;
                    tvTeamB_Score.setText(String.valueOf(teamBScore));
                }
                break;
            case R.id.btnMinus:
                if (radioTeamButton.getText().equals("Team A")) {
                    int localVal = teamAScore - spinnerValue;
                    if (localVal >= 0) {
                        teamAScore = localVal;
                        tvTeamA_Score.setText(String.valueOf(teamAScore));
                    } else {
                        Toast.makeText(this, "Scores cannot be in negative", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int localVal = teamBScore - spinnerValue;
                    if (localVal >= 0) {
                        teamBScore = localVal;
                        tvTeamB_Score.setText(String.valueOf(teamBScore));
                    } else {
                        Toast.makeText(this, "Scores cannot be in negative", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_winner:
                if (teamAScore > teamBScore) {
                    Toast.makeText(this, "Team A won the Game.", Toast.LENGTH_SHORT).show();
                } else if (teamAScore < teamAScore) {
                    Toast.makeText(this, "Team B won the Game.", Toast.LENGTH_SHORT).show();
                } else if (teamAScore == teamBScore) {
                    Toast.makeText(this, "It is Draw.", Toast.LENGTH_SHORT).show();
                }
                resetScores();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        spinnerValue = spinnerScores[pos];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void resetScores() {
        teamAScore = 0;
        teamBScore = 0;
        tvTeamA_Score.setText(String.valueOf(teamAScore));
        tvTeamB_Score.setText(String.valueOf(teamBScore));
    }
}