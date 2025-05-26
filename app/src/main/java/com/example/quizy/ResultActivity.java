package com.example.quizy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends Activity {

    private TextView textViewScore;
    private Button buttonRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewScore = findViewById(R.id.textViewScore);
        buttonRestart = findViewById(R.id.buttonRestart);

        int score = getIntent().getIntExtra("quizScore", 0);
        int total = getIntent().getIntExtra("totalQuestions", 0);

        textViewScore.setText("Your Score: " + score + "/" + total);

        buttonRestart.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
