package com.optimahorizonapps.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTimeTextView;
    private EditText mLapsTextView;
    private ScrollView mLapsScrollView;
    private Button mStartButton;
    private Button mLapButton;
    private Button mStopButton;


    private int mLapNum = 1;

    private Stopwatch mStopwatch;
    private Thread mThread;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mTimeTextView = findViewById(R.id.time_view);
        mLapsTextView = findViewById(R.id.editTextView_laps);
        mLapsScrollView = findViewById(R.id.scrollView_laps);
        mStartButton = findViewById(R.id.start_button);
        mLapButton = findViewById(R.id.lap_button);
        mStopButton = findViewById(R.id.stop_reset_button);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mStopwatch == null) {
                    mStopwatch = new Stopwatch(mContext);
                    mThread = new Thread(mStopwatch);
                    mThread.start();
                    mStopwatch.start();

                    mLapNum = 1;
                    mLapsTextView.setText("");
                }
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mStopwatch != null) {
                    mStopwatch.stop();
                    mThread.interrupt();
                    mThread = null;
                    mStopwatch = null;
                } else {
                    mTimeTextView.setText(R.string.zero_time);
                    mLapsTextView.setText(R.string.zeroTime_lapView);
                }

            }
        });

        mLapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStopwatch == null) {
                    mLapsTextView.setText("Stopwatch is not running!");
                    return;
                }

                mLapsTextView.append("LAP " + String.valueOf(mLapNum) + "  " + String.valueOf(mTimeTextView.getText()) + "\n");
                mLapNum++;
                mLapsScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mLapsScrollView.smoothScrollTo(0, mLapsTextView.getBottom());
                    }
                });
            }
        });
    }

    public void updateTimeView(final String time){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTimeTextView.setText(time);
            }
        });
    }
}