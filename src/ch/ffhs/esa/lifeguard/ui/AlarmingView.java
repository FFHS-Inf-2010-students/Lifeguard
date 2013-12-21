package ch.ffhs.esa.lifeguard.ui;

import ch.ffhs.esa.lifeguard.R;
import android.app.Activity;
import android.content.Intent;
import android.widget.Button;

public class AlarmingView
    implements ViewStateStrategy
{
    @Override
    public void handleUi (Activity activity, Intent intent)
    {
        Button button = (Button) activity.findViewById (R.id.SOSButton);
        button.setEnabled (false);
        button = (Button) activity.findViewById (R.id.toggleButtonAlarmSwitch);
        button.setEnabled (false);
        // TODO Maybe we should add a "cancel button"
    }

    @Override
    public void onClose ()
    {
    }
}
