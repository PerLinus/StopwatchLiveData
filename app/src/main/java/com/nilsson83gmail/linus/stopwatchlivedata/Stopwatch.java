package com.nilsson83gmail.linus.stopwatchlivedata;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class Stopwatch extends AppCompatActivity implements LifecycleObserver {



    private LiveDataStopwatch viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        viewModel = ViewModelProviders.of(this).get(LiveDataStopwatch.class);

        subscribe();
        getLifecycle().addObserver(new LiveDataStopwatch());
    }

    private void subscribe() {
        final Observer<Long> stopwatchObserver = new Observer<Long>() {
            @Override
            public void onChanged(@Nullable final Long aLong) {

                long hours = aLong /3600;
                long minutes = (aLong %3600)/60;
                long secs = aLong %60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                ((TextView) findViewById(R.id.time_view)).setText(time);
            }
        };
        viewModel.getStopwatch().observe(this,stopwatchObserver);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        super.onPause();
        viewModel.setOnPauseRunning(viewModel.isRunning());
        viewModel.setRunning(false);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        super.onResume();
        viewModel.setRunning(viewModel.isOnPauseRunning());
    }

    public void onClickStart(View view) {
        viewModel.setRunning(true);
    }

    public void onClickStop(View view) {
        viewModel.setRunning(false);
    }

    public void onClickReset(View view) {
        viewModel.setRunning(false);
        viewModel.setTime(0);
    }
}


