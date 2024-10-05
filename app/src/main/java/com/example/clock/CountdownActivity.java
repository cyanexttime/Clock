package com.example.clock;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CountdownActivity extends AppCompatActivity {
    private EditText countdownInput;
    private Spinner timeUnitSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_timer);

        countdownInput = findViewById(R.id.countdown_input);
        timeUnitSpinner = findViewById(R.id.time_unit_spinner);

        findViewById(R.id.start_timer_button).setOnClickListener(new View.OnClickListener() {
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
    }

    private void startCountdown(int time, String unit) {
        long milliseconds = convertToMilliseconds(time, unit);

        new CountDownTimer(milliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Toast.makeText(CountdownActivity.this, "Countdown finished!", Toast.LENGTH_SHORT).show();
            }
        }.start();
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
}
