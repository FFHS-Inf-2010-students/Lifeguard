package ch.ffhs.esa.lifeguard.ui;

import ch.ffhs.esa.lifeguard.ActivityMessage;
import ch.ffhs.esa.lifeguard.Lifeguard;
import ch.ffhs.esa.lifeguard.R;
import ch.ffhs.esa.lifeguard.alarm.state.AlarmStateId;
import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.CompoundButton;
import android.widget.TextView;

public class InitialView
    implements ViewStateStrategy
{
    Activity activity;

    private CompoundButton.OnCheckedChangeListener tickToggleListener
        = new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged (CompoundButton buttonView, boolean isChecked)
            {
                buttonView.setEnabled (false);
                triggerStartTicking ();
            }
        };

    @Override
    public void handleUi (Activity activity, Intent intent)
    {
        this.activity = activity;

        Button button = (Button) activity.findViewById (R.id.SOSButton);
        button.setEnabled (true);

        CompoundButton tickButton = (CompoundButton) activity.findViewById (R.id.toggleButtonAlarmSwitch);

        tickButton.setOnCheckedChangeListener (null);
        tickButton.setChecked (false);
        tickButton.setEnabled (true);
        tickButton.setOnCheckedChangeListener (tickToggleListener);

        ProgressBar bar = (ProgressBar) activity.findViewById(R.id.progressBarDelay);
        bar.setProgress(bar.getMax ());

        long maxTick
            = Long.parseLong(
                activity
                .getSharedPreferences (Lifeguard.APPLICATION_SETTINGS, 0)
                .getString ("alarmDelay", "600"));

        TextView delayText = (TextView) activity.findViewById (R.id.textViewDelay);

        delayText.setText (ClockTickFormatter.format (0, maxTick));
    }

    @Override
    public void onClose ()
    {}

    private void triggerStartTicking ()
    {
        Intent intent = new Intent (ActivityMessage.STATE_CHANGE_REQUEST);
        intent.putExtra ("stateId", AlarmStateId.TICKING.toString ());
        activity.sendBroadcast (intent);
    }
}
