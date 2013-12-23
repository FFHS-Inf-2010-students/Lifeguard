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
import android.widget.TextView;

public class TickingView
    implements ViewStateStrategy
{
    private BroadcastReceiver stateChangeListener = new BroadcastReceiver () {
        @Override
        public void onReceive (Context context, Intent intent)
        { onStateChange (intent); }
    };

    private BroadcastReceiver tickListener = new BroadcastReceiver() {
        @Override
        public void onReceive (Context context, Intent intent)
        { onTick (intent); }
    };

    private CompoundButton.OnCheckedChangeListener tickToggleListener
        = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged (CompoundButton buttonView, boolean isChecked)
        {
            buttonView.setEnabled (false);
            cancelTicking ();
        }
    };

    private Activity activity;

    private boolean listening = false;

    private ProgressBar bar;

    private TextView delayText;

    @Override
    public void handleUi (Activity activity, Intent intent)
    {
        this.activity = activity;
        bar = (ProgressBar) activity.findViewById (R.id.progressBarDelay);
        delayText = (TextView) activity.findViewById (R.id.textViewDelay);

        TextView sosText = (TextView) activity.findViewById (R.id.textViewSOSButton);
        sosText.setText ("");

        startListening ();
    }

    private void onStateChange (Intent intent)
    {
        AlarmStateId alarmId = AlarmStateId.valueOf (
                AlarmStateId.class,
                intent.getExtras ().getString (ServiceMessage.Key.ALARM_STATE_ID));
        if (alarmId != AlarmStateId.TICKING) {
            stopListening ();
        }
    }

    private void onTick (Intent intent)
    {
        Bundle bundle = intent.getExtras ();

        long maxTick = bundle.getLong (ServiceMessage.Key.MAX_CLOCK_TICK);
        if (maxTick < 1) maxTick = 1;

        long tick = bundle.getLong (ServiceMessage.Key.CLOCK_TICK, 0);
        int maxProgress = bar.getMax ();

        /* Take the inverse - the amount to still tick instead of how long
         * already ticked. */
        double percentage = ((double) maxTick - tick) / maxTick;
        bar.setProgress ((int) (maxProgress * percentage));

        delayText.setText (ClockTickFormatter.format (tick, maxTick));
    }

    private void cancelTicking ()
    {
        Intent intent = new Intent (ActivityMessage.STATE_CHANGE_REQUEST);
        intent.putExtra (ActivityMessage.Key.ALARM_STATE_ID,
                AlarmStateId.INIT.toString ());
        activity.sendBroadcast (intent);
    }

    private void startListening ()
    {
        if (listening) {
            stopListening ();
        }

        activity.registerReceiver (
                stateChangeListener,
                new IntentFilter (ServiceMessage.CURRENT_SERVICE_STATE));
        activity.registerReceiver (
                tickListener,
                new IntentFilter (ServiceMessage.ALARM_CLOCK_TICK));

        CompoundButton tickToggleButton = (CompoundButton) activity.
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
