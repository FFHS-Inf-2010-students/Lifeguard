package ch.ffhs.esa.lifeguard.ui;

import ch.ffhs.esa.lifeguard.R;
import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

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
        
        TextView sosText = (TextView)activity.findViewById(R.id.textViewSOSButton);
        sosText.setText(intent.getExtras().getString("rescuer").toString() + " eilt zur Hilfe!");
    }

    @Override
    public void onClose ()
    {
    }
}
