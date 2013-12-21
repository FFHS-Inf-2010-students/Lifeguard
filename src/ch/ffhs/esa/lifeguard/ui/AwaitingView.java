package ch.ffhs.esa.lifeguard.ui;

import ch.ffhs.esa.lifeguard.R;
import android.app.Activity;
import android.content.Intent;
import android.widget.Button;

public class AwaitingView
    implements ViewStateStrategy
{
    @Override
    public void handleUi (Activity activity, Intent intent)
    {
        Button button = (Button) activity.findViewById (R.id.SOSButton);
        button.setEnabled (false);
        button = (Button) activity.findViewById (R.id.toggleButtonAlarmSwitch);
        button.setEnabled (false);
        
        //Long id = Long.valueOf (intent.getExtras ().get ("rescuer").toString ());
        //Contacts contacts = new Contacts (Lifeguard.getDatabaseHelper ());
        //ContactInterface contact = contacts.findById (id);
        
    }

    @Override
    public void onClose ()
    {
    }
}
