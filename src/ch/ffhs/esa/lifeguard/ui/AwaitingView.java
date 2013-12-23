package ch.ffhs.esa.lifeguard.ui;

import java.text.MessageFormat;

import ch.ffhs.esa.lifeguard.ActivityMessage;
import ch.ffhs.esa.lifeguard.R;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import ch.ffhs.esa.lifeguard.Lifeguard;
import ch.ffhs.esa.lifeguard.domain.ContactInterface;
import ch.ffhs.esa.lifeguard.domain.Contacts;
import ch.ffhs.esa.lifeguard.alarm.ServiceMessage;

public class AwaitingView
    implements ViewStateStrategy
{
    private Contacts contacts;

    private Activity app;

    /**
     * Enables the cancel button and displays a message about the sent alarm.
     * @param activity the activitiy
     * @param Intent the intent from the message
     */
    @Override
    public void handleUi (Activity activity, Intent intent)
    {
        app = activity;
        if (contacts == null) {
            contacts = new Contacts (Lifeguard.getDatabaseHelper ());
        }

        long contactId = intent.getExtras ().getLong (ServiceMessage.Key.CONTACT_ID);

        ContactInterface contact = contacts.findById (contactId);
        if (contact == null) {
            throw new IllegalStateException ("Cannot find the contact with id " + contactId);
        }

        CompoundButton tickToggleButton = (CompoundButton) activity.
                findViewById (R.id.toggleButtonAlarmSwitch);
        tickToggleButton.setEnabled (false);

        ProgressBar bar = (ProgressBar) activity.findViewById(R.id.progressBarDelay);
        bar.setProgress(0);

        TextView delayText = (TextView) activity.findViewById (R.id.textViewDelay);
        delayText.setText (ClockTickFormatter.format (0, 0));

        Button sosButton = (Button) activity.findViewById (R.id.SOSButton);
        sosButton.setText(R.string.main_label_cancel);

        sosButton.setOnTouchListener (SOSButtonListener.create (
                new Runnable () { public void run () { triggerManualCancel (); }}));
        sosButton.setEnabled (true);

        TextView textView = (TextView) activity.findViewById (R.id.textViewSOSButton);
        textView.setText (createAlarmDisplayMessage (activity, contact));
    }

    @Override
    public void onClose ()
    {
    }

    private String createAlarmDisplayMessage (Activity activity, ContactInterface contact)
    {
        Resources resources = activity.getResources ();
        final String messageFormat = resources.getString (
                R.string.alarm_display_message_text);

        return MessageFormat.format (messageFormat,
                contact.getName (),
                contact.getPhone ());
    }

    private void triggerManualCancel ()
    {
        app.sendBroadcast (new Intent (ActivityMessage.CANCEL_OPERATION));
    }
}
