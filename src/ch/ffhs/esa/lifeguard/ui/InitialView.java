package ch.ffhs.esa.lifeguard.ui;

import ch.ffhs.esa.lifeguard.ActivityMessage;
import ch.ffhs.esa.lifeguard.R;
import ch.ffhs.esa.lifeguard.alarm.state.AlarmStateId;
import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

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
                triggerStartTicking ();
                buttonView.setEnabled (false);
            }
        };

    @Override
    public void handleUi (Activity activity, Intent intent)
    {
        this.activity = activity;

        Button button = (Button) activity.findViewById (R.id.SOSButton);
        button.setEnabled (true);

        ToggleButton tickButton = (ToggleButton) activity.findViewById (R.id.toggleButtonAlarmSwitch);

        tickButton.setOnCheckedChangeListener (null);
        tickButton.setChecked (false);
        tickButton.setEnabled (true);
        tickButton.setOnCheckedChangeListener (tickToggleListener);
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
