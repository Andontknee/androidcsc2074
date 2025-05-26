package com.example.quizy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String TAG = "QuizyApp";

    private TextView questionTextView;
    private Button trueButton, falseButton, previousButton, nextButton;

    private Question[] questionBank = new Question[] {
            new Question("The sky is blue.", true),
            new Question("2 + 2 equals 5.", false),
            new Question("The earth is flat.", false),
            new Question("Fire is cold.", false),
            new Question("Water boils at 100°C.", true)
    };

    private int currentIndex = 0;
    private int score = 0;
    private boolean[] answered = new boolean[questionBank.length]; // To prevent double scoring

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.question_text_view);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        previousButton = findViewById(R.id.previous_button);
        nextButton = findViewById(R.id.next_button);

        updateQuestion();

        trueButton.setOnClickListener(v -> {
            checkAnswer(true);
            moveToNextQuestion();
        });

        falseButton.setOnClickListener(v -> {
            checkAnswer(false);
            moveToNextQuestion();
        });

        previousButton.setOnClickListener(v -> moveToPreviousQuestion());

        nextButton.setOnClickListener(v -> moveToNextQuestion());
    }

    private void updateQuestion() {
        questionTextView.setText(questionBank[currentIndex].getQuestionText());
    }

    private void moveToNextQuestion() {
        if (currentIndex == questionBank.length - 1) {
            // End of quiz: launch ResultActivity
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("quizScore", score);
            intent.putExtra("totalQuestions", questionBank.length);
            startActivity(intent);
            finish();
        } else {
            currentIndex++;
            Log.d(TAG, "Current index: " + currentIndex);
            updateQuestion();
            Toast.makeText(this, "Next Question Loading...", Toast.LENGTH_SHORT).show();
        }
    }

    private void moveToPreviousQuestion() {
        currentIndex = (currentIndex - 1 + questionBank.length) % questionBank.length;
        Log.d(TAG, "Current index: " + currentIndex);
        updateQuestion();
        Toast.makeText(this, "Previous Question Loading...", Toast.LENGTH_SHORT).show();
    }

    private void checkAnswer(boolean userAnswer) {
        boolean correctAnswer = questionBank[currentIndex].isAnswerTrue();
        if (!answered[currentIndex]) {
            if (userAnswer == correctAnswer) {
                score++;
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Answer was Correct.");
            } else {
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Answer was Incorrect.");
            }
            answered[currentIndex] = true;
        } else {
            Toast.makeText(this, "Already answered.", Toast.LENGTH_SHORT).show();
        }
    }
}
