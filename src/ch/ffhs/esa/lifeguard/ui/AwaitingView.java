package ch.ffhs.esa.lifeguard.ui;

import ch.ffhs.esa.lifeguard.Lifeguard;
import ch.ffhs.esa.lifeguard.domain.ContactInterface;
import ch.ffhs.esa.lifeguard.domain.Contacts;
import android.app.Activity;
import android.content.Intent;

public class AwaitingView
    implements ViewStateStrategy
{
    @Override
    public void handleUi (Activity activity, Intent intent)
    {
//        Long id = Long.valueOf (intent.getExtras ().get ("rescuer").toString ());
//        Contacts contacts = new Contacts (Lifeguard.getDatabaseHelper ());
//        ContactInterface contact = contacts.findById (id);
    }
}
