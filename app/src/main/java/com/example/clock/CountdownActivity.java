package com.example.clock;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.app.AppCompatActivity;

public class CountdownActivity extends AppCompatActivity {
    private EditText countdownInput;
    private Spinner timeUnitSpinner;
    private TextView timerDisplay;
    private Button startTimerButton;
    private Button endTimerButton;
    private Button backButton; // Thêm biến cho nút Back
    private CountDownTimer countDownTimer;
    private long remainingTimeInMillis;
    private boolean isTimerRunning = false; // Biến để theo dõi trạng thái đồng hồ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_timer);

        countdownInput = findViewById(R.id.countdown_input);
        timeUnitSpinner = findViewById(R.id.time_unit_spinner);
        timerDisplay = findViewById(R.id.timer_display);
        startTimerButton = findViewById(R.id.start_timer_button);
        endTimerButton = findViewById(R.id.end_timer_button);
        backButton = findViewById(R.id.back_button); // Khởi tạo nút Back

        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning) {
                    endCountdown(); // Dừng đồng hồ nếu đang chạy
                    resetTimer(); // Reset màn hình
                }
                String timeText = countdownInput.getText().toString();
                String unit = timeUnitSpinner.getSelectedItem().toString();

                if (!timeText.isEmpty()) {
                    int time = Integer.parseInt(timeText);
                    timerDisplay.setText("00:00");
                    startCountdown(time, unit);
                    hideKeyboard(v);
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

        // Thêm listener cho nút Back
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng activity hiện tại và quay lại activity trước đó
            }
        });
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
                isTimerRunning = false; // Đặt trạng thái là không chạy
                timerDisplay.setText("00:00");
                Toast.makeText(CountdownActivity.this, "Countdown finished!", Toast.LENGTH_SHORT).show();
            }
        }.start();

        isTimerRunning = true; // Đặt trạng thái là đang chạy
    }

    private void endCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            isTimerRunning = false; // Đặt trạng thái là không chạy
            timerDisplay.setText("00:00");
            Toast.makeText(CountdownActivity.this, "Countdown stopped!", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetTimer() {
        timerDisplay.setText("00:00"); // Reset hiển thị timer
        timeUnitSpinner.setSelection(0); // Reset spinner về giá trị mặc định
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