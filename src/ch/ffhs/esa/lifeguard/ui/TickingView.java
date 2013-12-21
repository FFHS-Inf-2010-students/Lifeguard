package ch.ffhs.esa.lifeguard.ui;

import ch.ffhs.esa.lifeguard.ActivityMessage;
import ch.ffhs.esa.lifeguard.R;
import ch.ffhs.esa.lifeguard.alarm.ServiceMessage;
import ch.ffhs.esa.lifeguard.alarm.state.AlarmStateId;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

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

    private CompoundButton.OnCheckedChangeListener tickToggleListener
        = new CompoundButton.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged (CompoundButton buttonView, boolean isChecked)
        {
            buttonView.setEnabled (false);
            triggerManualCancel ();
        }
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

        long maxTick = bundle.getLong ("maxClockTick");
        if (maxTick < 1) maxTick = 1;

        long tick = bundle.getLong ("clockTick", 0);
        int maxProgress = bar.getMax ();

        /* Take the inverse - the amount to still tick instead of how long
         * already ticked. */
        double percentage = ((double) maxTick - tick) / maxTick;

        bar.setProgress ((int) (maxProgress * percentage));
    }

    private void triggerManualCancel ()
    {
        activity.sendBroadcast (new Intent (ActivityMessage.CANCEL_OPERATION));
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

        ToggleButton tickToggleButton = (ToggleButton) activity.
                findViewById (R.id.toggleButtonAlarmSwitch);
        tickToggleButton.setOnCheckedChangeListener (null);
        tickToggleButton.setChecked (true);
        tickToggleButton.setEnabled (true);
        tickToggleButton.setOnCheckedChangeListener (tickToggleListener);

        listening = true;
    }

    private void stopListening ()
    {
        if (listening) {
            activity.unregisterReceiver (stateChangeListener);
            activity.unregisterReceiver (tickListener);

            listening = false;
        }
    }

    @Override
    public void onClose ()
    {
        stopListening ();
    }
}
