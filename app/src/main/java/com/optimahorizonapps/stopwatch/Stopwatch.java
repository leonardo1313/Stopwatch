package com.optimahorizonapps.stopwatch;

import android.content.Context;

public class Stopwatch implements Runnable {

    public static final long MILLIS_TO_MINUTES = 60000;
    public static final long MILLIS_TO_HOURS = 3600000;
    private Context mContext;

    private long mStartTime;
    private boolean isTimeRunning;

    public Stopwatch(Context context) {
        mContext = context;
    }

    public void start(){
        mStartTime = System.currentTimeMillis();
        isTimeRunning = true;
    }
    public void stop(){
        isTimeRunning = false;
    }

    @Override
    public void run() {

        while (isTimeRunning){

            long since = System.currentTimeMillis() - mStartTime;

                int millis = (int) since % 1000;
                int seconds = (int) ((since / 1000) % 60);
                int minutes = (int) ((since / MILLIS_TO_MINUTES) % 60);
                int hours = (int) ((since / MILLIS_TO_HOURS) % 24);

            ((MainActivity) mContext).updateTimeView(String.format("%02d:%02d:%02d:%03d",
                    hours, minutes, seconds, millis));
        }
    }
}
