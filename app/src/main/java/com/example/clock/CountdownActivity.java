package com.example.clock;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CountdownActivity extends AppCompatActivity {
    private EditText countdownInput;
    private Spinner timeUnitSpinner;
    private TextView timerDisplay;
    private Button startTimerButton;
    private Button endTimerButton;
    private CountDownTimer countDownTimer;
    private long remainingTimeInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_timer);

        countdownInput = findViewById(R.id.countdown_input);
        timeUnitSpinner = findViewById(R.id.time_unit_spinner);
        timerDisplay = findViewById(R.id.timer_display);
        startTimerButton = findViewById(R.id.start_timer_button);
        endTimerButton = findViewById(R.id.end_timer_button);

        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeText = countdownInput.getText().toString();
                String unit = timeUnitSpinner.getSelectedItem().toString();

                if (!timeText.isEmpty()) {
                    int time = Integer.parseInt(timeText);
                    startCountdown(time, unit);
                } else {
                    Toast.makeText(CountdownActivity.this, "Please enter a valid time", Toast.LENGTH_SHORT).show();
                }
            }
        });

        endTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endCountdown();
            }
        });
    }

    private void startCountdown(int time, String unit) {
        remainingTimeInMillis = convertToMilliseconds(time, unit);

        countDownTimer = new CountDownTimer(remainingTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTimeInMillis = millisUntilFinished;
                timerDisplay.setText(formatTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                timerDisplay.setText("00:00");
                Toast.makeText(CountdownActivity.this, "Countdown finished!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    private void endCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            timerDisplay.setText("00:00");  // Reset về 00:00
            Toast.makeText(CountdownActivity.this, "Countdown stopped!", Toast.LENGTH_SHORT).show();
        }
    }

    private long convertToMilliseconds(int time, String unit) {
        switch (unit) {
            case "Seconds":
                return time * 1000L;
            case "Minutes":
                return time * 60 * 1000L;
            case "Hours":
                return time * 60 * 60 * 1000L;
            default:
                return time * 1000L; // Default to seconds if something goes wrong
        }
    }

    private String formatTime(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}