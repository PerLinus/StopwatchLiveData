package com.nilsson83gmail.linus.stopwatchlivedata;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Linus on 2018-01-09.
 */

public class LiveDataStopwatch extends ViewModel {

    private static final int ONE_SECOND = 1000;

    private boolean running;

    private MutableLiveData<Long> stopwatch = new MutableLiveData<>();

    private long time;

    public LiveDataStopwatch() {
        stopwatch.postValue(time);
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                if (running) {
                    final long newValue = time++;
                    stopwatch.postValue(newValue);
                }
            }
        }, ONE_SECOND, ONE_SECOND);
    }

    public LiveData<Long> getStopwatch() {
        return stopwatch;
    }

    public void setTime(long time) {
        this.time = time;
        stopwatch.postValue(time);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}

