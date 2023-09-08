package com.hfad.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Locale;

public class StopwatchActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private ArrayList<Integer> vueltaTimes = new ArrayList<>();
    private TextView lapTimesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lapTimesTextView = findViewById(R.id.vuelta_times);

        if (savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            vueltaTimes = savedInstanceState.getIntegerArrayList("ventanaTimes");
            updateLapTimesView();
        }

        runTimer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putIntegerArrayList("vueltaTimes", vueltaTimes);
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
        vueltaTimes.clear();
        //actualiza el texto con los tiempos de vuelta
        updateLapTimesView();
    }

    public void onClickVuelta(View view) {
        if (running) {
            vueltaTimes.add(seconds);
            updateLapTimesView();
        }
    }

    public void runTimer() {
        final TextView timeView = findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds% 3600)/60;
                int secs = seconds% 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void updateLapTimesView() {
        StringBuilder lapTimesText = new StringBuilder();
        for (int i = 0; i < vueltaTimes.size(); i++) {
            int vueltaTime = vueltaTimes.get(i);
            int hours = vueltaTime / 3600;
            int minutes = (vueltaTime % 3600) / 60;
            int secs = vueltaTime % 60;
            lapTimesText.append(String.format(Locale.getDefault(), "Vuelta %d: %02d:%02d:%02d\n", i+1, hours, minutes, secs));
        }
        lapTimesTextView.setText(lapTimesText.toString());
    }
}
