package ch.ffhs.esa.lifeguard.ui;

import ch.ffhs.esa.lifeguard.ActivityMessage;
import ch.ffhs.esa.lifeguard.R;
import android.app.Activity;
import android.content.Intent;
import android.widget.Button;

public class AlarmingView
    implements ViewStateStrategy
{
    private Activity app;

    @Override
    public void handleUi (Activity activity, Intent intent)
    {
        app = activity;

        Button tickButton = (Button) activity.findViewById (R.id.toggleButtonAlarmSwitch);
        tickButton.setEnabled (false);

        Button sosButton = (Button) activity.findViewById (R.id.SOSButton);
        sosButton.setEnabled (false);
        sosButton.setText(R.string.main_label_cancel);

        sosButton.setOnTouchListener (SOSButtonListener.create (
                new Runnable () { public void run () { triggerManualCancel (); }}));
        sosButton.setEnabled (true);
    }

    @Override
    public void onClose ()
    {
    }

    private void triggerManualCancel ()
    {
        app.sendBroadcast (new Intent (ActivityMessage.CANCEL_OPERATION));
    }
}
