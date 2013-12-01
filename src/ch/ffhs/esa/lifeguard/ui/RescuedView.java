package ch.ffhs.esa.lifeguard.ui;

import ch.ffhs.esa.lifeguard.R;
import android.app.Activity;
import android.content.Intent;
import android.widget.Button;

public class RescuedView
    implements ViewStateStrategy
{
    @Override
    public void handleUi (Activity activity, Intent intent)
    {
        Button button = (Button) activity.findViewById (R.id.SOSButton);
        button.setEnabled (true);
        button = (Button) activity.findViewById (R.id.toggleButtonAlarmSwitch);
        button.setEnabled (true);
        // TODO Show the user that he will be rescued
    }
}
