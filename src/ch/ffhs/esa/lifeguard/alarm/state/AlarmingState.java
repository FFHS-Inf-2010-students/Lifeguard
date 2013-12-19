package ch.ffhs.esa.lifeguard.alarm.state;

import java.util.ArrayList;

import ch.ffhs.esa.lifeguard.Lifeguard;
import ch.ffhs.esa.lifeguard.alarm.SmsDeliveredReceiver;
import ch.ffhs.esa.lifeguard.alarm.SmsSentReceiver;
import ch.ffhs.esa.lifeguard.domain.ContactInterface;
import ch.ffhs.esa.lifeguard.domain.Contacts;
import ch.ffhs.esa.lifeguard.R;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Sends an alarm message to the rescuer.
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 */
public class AlarmingState extends AbstractAlarmState {

    private Contacts contacts;
    private int contactPosition;
    private int nrOfContacts = -1;

    /*
     * //////////////////////////////////////////////////////////////////////////
     * PUBLIC INTERFACE
     */

    public AlarmingState ()
    {
        this (0);
    }

    public AlarmingState (int contactIndex)
    {
        this.contactPosition = contactIndex;
        this.contacts = new Contacts (Lifeguard.getDatabaseHelper ());
    }

	@Override
    public AlarmStateId getId ()
    {
        return AlarmStateId.ALARMING;
    }

    /*
     * //////////////////////////////////////////////////////////////////////////
     * PROTECTED OPERATIONS
     */
    
    @Override
    protected void doProcess () {
//        contactList = new Contacts(Lifeguard.getDatabaseHelper()).getAll().toArray();
        ContactInterface recipient = getNextContact ();
        Log.d (this.getClass ().toString (), "doProcess ALarmingState");
        Log.d (this.getClass ().toString (),
                "Try to notify " + recipient.getName () + " ("
                        + recipient.getPhone () + ")");
        notifyRecipient (recipient);
        getContext ().setNext (
                new AwaitingState (recipient));
    }
    

    private ContactInterface getNextContact() throws IllegalStateException {
        contactPosition++;
        if (nrOfContacts < 0) {
            nrOfContacts = contacts.getCount ().intValue ();
        }
        if (contactPosition >= nrOfContacts) {
            /* Circulate through all contacts over and over, help is needed! */
            contactPosition = 1;
        }

        ContactInterface contact = contacts.findByPosition (contactPosition);
        if (null == contact) {
            throw new IllegalStateException ("Cannot retrieve the next contact.");
        }

        return contact;
    }

    private void notifyRecipient(ContactInterface recipient) {
        String phoneNumber = recipient.getPhone();
        
        ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();
        PendingIntent sentPI = PendingIntent.getBroadcast(getBaseContext (), 0, new Intent(getBaseContext (), SmsSentReceiver.class), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(getBaseContext (), 0, new Intent(getBaseContext (), SmsDeliveredReceiver.class), 0);
        try {
            SmsManager sms = SmsManager.getDefault();
            ArrayList<String> mSMSMessage = sms.divideMessage(getAlarmMessage ());
            for (int i = 0; i < mSMSMessage.size(); i++) {
                sentPendingIntents.add(i, sentPI);
                deliveredPendingIntents.add(i, deliveredPI);
            }
            sms.sendMultipartTextMessage(phoneNumber, null, mSMSMessage,
                    sentPendingIntents, deliveredPendingIntents);

        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(getBaseContext (), "SMS sending failed...",Toast.LENGTH_SHORT).show();
        }
    }

    private String getAlarmMessage ()
    {
        Context context = getBaseContext ();
        Resources resources = context.getResources ();

        final String format = resources.getString (
                R.string.alarm_message_format_string).trim ();
        final String entryForEmpty = resources.getString (
                R.string.alarm_message_default_entry);

        SharedPreferences prefs = context.getSharedPreferences (
                Lifeguard.APPLICATION_SETTINGS, 0);

        final String userName = prefs.getString ("userName", entryForEmpty);

        return String.format (format, userName);
    }
}
