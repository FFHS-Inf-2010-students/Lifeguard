package ch.ffhs.esa.lifeguard.alarm.state;

import java.util.Timer;
import java.util.TimerTask;

import ch.ffhs.esa.lifeguard.alarm.ServiceMessage;
import ch.ffhs.esa.lifeguard.domain.ContactInterface;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Awaiting the reply from the rescuer.
 *
 * @author David Daniel <david.daniel@students.ffhs.ch>
 */
public class AwaitingState extends AbstractAlarmState
{
    private class SmsReceiver
        extends BroadcastReceiver
    {
        @Override
        public void onReceive (Context context, Intent intent)
        { receiveSms (context, intent); }
    }

    private SmsReceiver smsReceiver = new SmsReceiver ();

    private ContactInterface contact;

    private Timer timer;

    /*
     * //////////////////////////////////////////////////////////////////////////
     * PUBLIC INTERFACE
     */

    /**
     * Creates a new awaiting state that waits for a reply of the given contact.
     * @param contact the contact to await the reply of
     */
    public AwaitingState (ContactInterface contact)
    {
        this.contact = contact;
    }

    @Override
    public AlarmStateId getId ()
    {
        return AlarmStateId.AWAITING;
    }

    /**
     * Cancels the listening on sms arrivals and the timeout.
     */
    @Override
    public void cancel ()
    {
        Context base = getContext ().getBaseContext ();
        base.unregisterReceiver (smsReceiver);
        if (timer != null) {
            timer.cancel ();
            timer = null;
        }
    }

    /*
     * //////////////////////////////////////////////////////////////////////////
     * PROTECTED OPERATIONS
     */

    @Override
    protected void doProcess ()
    {
        registerReceiver ();
        registerTimeout ();
    }

    /**
     * Registers the broadcast receiver for getting notified on sms arrival.
     */
    protected void registerReceiver ()
    {
        IntentFilter filter = new IntentFilter ("android.provider.Telephony.SMS_RECEIVED");
        getContext ().getBaseContext ().registerReceiver (smsReceiver, filter);
    }

    /**
     * Registers the timeout.
     */
    protected void registerTimeout ()
    {
        if (timer != null) {
            timer.cancel ();
        }
        timer = new Timer (true);
        TimerTask timeout = new TimerTask() {
            public void run ()
            {
                cancel ();
                Intent intent = new Intent (ServiceMessage.ALARM_REPEATED);
                intent.putExtra ("receiver", contact.getId ());
                getContext ().getBaseContext ().sendBroadcast (intent);
                getContext ().setNext (new AlarmingState (contact.getPosition ()));
            }
        };
        /* TODO use a valid delay or calculate date */
        long delay = 1000000;
        timer.schedule (timeout, delay);
    }

    /**
     * Handles incoming sms messages.
     */
    protected void receiveSms (Context context, Intent intent)
    {
        Bundle bundle = intent.getExtras ();
        boolean helpConfirmed = false;

        if (bundle != null) {
            //---retrieve the SMS message received---
            // @see http://stackoverflow.com/questions/8814751/how-to-store-messages-in-list-received-from-broadcast-receiver
            Object [] pdus = (Object[]) bundle.get ("pdus");

            for (int i = 0; i < pdus.length; ++i) {
                SmsMessage message = SmsMessage.createFromPdu ((byte []) pdus [i]);
                String senderNumber = getNormalizedPhone (
                        message.getOriginatingAddress ());

                if (senderNumber.equals (getNormalizedPhone (contact.getPhone ()))) {
                    helpConfirmed = true;
                    break;
                }
            }
        }

        if (helpConfirmed) {
            cancel ();
            Intent message = new Intent (ServiceMessage.RESCUE_CONFIRMED);
            message.putExtra ("rescuer", contact.getId ());
            getContext ().getBaseContext ().sendBroadcast (message);

            getContext ().setNext (new ConfirmedState ());
        }
    }

    /**
     * Removes everything but digits from the given phone number.
     * @param original the phone number to normalize
     * @return the normalized phone number
     */
    private String getNormalizedPhone (String original)
    {
        return original.replaceAll ("[^\\d]", "");
    }
}
