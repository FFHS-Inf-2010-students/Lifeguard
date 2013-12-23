package ch.ffhs.esa.lifeguard.ui;

import ch.ffhs.esa.lifeguard.R;
import ch.ffhs.esa.lifeguard.Lifeguard;
import ch.ffhs.esa.lifeguard.alarm.ServiceMessage;
import ch.ffhs.esa.lifeguard.domain.Contacts;
import ch.ffhs.esa.lifeguard.domain.ContactInterface;

import android.app.Activity;
import android.widget.TextView;
import android.content.Intent;
import android.content.res.Resources;

import java.text.MessageFormat;

public class RescuedView
    implements ViewStateStrategy
{
    Contacts contacts = null;

    @Override
    public void handleUi (Activity activity, Intent intent)
    {
        if (contacts == null) {
            contacts = new Contacts (Lifeguard.getDatabaseHelper ());
        }

        final long id = intent.getExtras ().getLong (ServiceMessage.Key.CONTACT_ID);

        ContactInterface contact = contacts.findById (id);
        if (contacts == null) {
            throw new IllegalStateException ("Cannot find the rescuer with the id " + id);
        }

        TextView sosText = (TextView)activity.findViewById(R.id.textViewSOSButton);
        sosText.setText(getConfirmationMesssage (activity, contact));
    }

    @Override
    public void onClose ()
    {
    }

    private String getConfirmationMesssage (Activity activity, ContactInterface contact)
    {
        Resources resources = activity.getResources ();
        final String format = resources.getString (
                R.string.alarm_confirmation_message_text).trim ();

        return MessageFormat.format (format,
                contact.getName (),
                contact.getPhone ());
    }
}
