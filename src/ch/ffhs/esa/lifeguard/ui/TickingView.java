package ch.ffhs.esa.lifeguard.ui;

import ch.ffhs.esa.lifeguard.R;
import ch.ffhs.esa.lifeguard.alarm.ServiceMessage;
import ch.ffhs.esa.lifeguard.alarm.state.AlarmStateId;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ProgressBar;

public class TickingView
    implements ViewStateStrategy
{
    private BroadcastReceiver stateChangeListener = new BroadcastReceiver () {
        @Override
        public void onReceive (Context context, Intent intent)
        { onStateChange (intent); }
    };

    private BroadcastReceiver tickListener = new BroadcastReceiver()
    {
        @Override
        public void onReceive (Context context, Intent intent)
        { onTick (intent); }
    };

    private Activity activity;

    private boolean listening = false;

    private ProgressBar bar;

    @Override
    public void handleUi (Activity activity, Intent intent)
    {
        this.activity = activity;
        bar = (ProgressBar) activity.findViewById (R.id.progressBarDelay);
        startListening ();
    }

    private void onStateChange (Intent intent)
    {
        AlarmStateId alarmId = AlarmStateId.valueOf (
                AlarmStateId.class,
                intent.getExtras ().getString ("stateId"));
        if (alarmId != AlarmStateId.TICKING) {
            stopListening ();
        }
    }

    private void onTick (Intent intent)
    {
        Bundle bundle = intent.getExtras ();
        int maxTick = bundle.getInt ("maxClockTick");
        if (maxTick < 1) maxTick = 1;
        int tick = bundle.getInt ("clockTick");

        bar.setProgress (bar.getMax () * (tick / maxTick));
    }

    private void startListening ()
    {
        bar.setProgress (0);
        if (listening) {
            stopListening ();
        }

        activity.registerReceiver (
                stateChangeListener,
                new IntentFilter (ServiceMessage.CURRENT_SERVICE_STATE));
        activity.registerReceiver (
                tickListener,
                new IntentFilter (ServiceMessage.ALARM_CLOCK_TICK));

        listening = true;
    }

    private void stopListening ()
    {
        bar.setProgress (0);
        if (listening) {
            activity.unregisterReceiver (stateChangeListener);
            activity.unregisterReceiver (tickListener);
            listening = false;
        }
    }
}
