package ch.ffhs.esa.lifeguard.ui;

import java.text.MessageFormat;

import ch.ffhs.esa.lifeguard.R;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.TextView;
import ch.ffhs.esa.lifeguard.Lifeguard;
import ch.ffhs.esa.lifeguard.domain.ContactInterface;
import ch.ffhs.esa.lifeguard.domain.Contacts;
import ch.ffhs.esa.lifeguard.alarm.ServiceMessage;

public class AwaitingView
    implements ViewStateStrategy
{
    Contacts contacts;

    @Override
    public void handleUi (Activity activity, Intent intent)
    {
        if (contacts == null) {
            contacts = new Contacts (Lifeguard.getDatabaseHelper ());
        }

        long contactId = intent.getExtras ().getLong (ServiceMessage.Key.CONTACT_ID);

        ContactInterface contact = contacts.findById (contactId);
        if (contact == null) {
            throw new IllegalStateException ("Cannot find the contact with id " + contactId);
        }

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
}
