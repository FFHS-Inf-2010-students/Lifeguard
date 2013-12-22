package ch.ffhs.esa.lifeguard.alarm.state;

import java.util.Timer;
import java.util.TimerTask;

import ch.ffhs.esa.lifeguard.Lifeguard;
import ch.ffhs.esa.lifeguard.R;
import ch.ffhs.esa.lifeguard.alarm.ServiceMessage;
import ch.ffhs.esa.lifeguard.domain.ContactInterface;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

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

    private Timer timer = null;

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
        Context base = getAlarmContext ().getAndroidContext ();
        base.unregisterReceiver (smsReceiver);
        if (timer != null) {
            timer.cancel ();
            timer = null;
        }
    }

    @Override
    public void putStateInfo (Intent intent)
    {
        intent.putExtra (ServiceMessage.Key.CONTACT_ID, contact.getId ());
    }

    /*
     * //////////////////////////////////////////////////////////////////////////
     * PROTECTED OPERATIONS
     */

    @Override
    protected void start ()
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
        getAlarmContext ().getAndroidContext ().registerReceiver (smsReceiver, filter);
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
                intent.putExtra (ServiceMessage.Key.ALARM_RECEIVER_ID, contact.getId ());
                getAlarmContext ().getAndroidContext ().sendBroadcast (intent);
                getAlarmContext ().setNext (new AlarmingState (contact.getPosition ()));
            }
        };
        SharedPreferences prefs = getAndroidContext ().getSharedPreferences (
                Lifeguard.APPLICATION_SETTINGS, 0);
        //default timeout 10min
        long delay
            = Long.parseLong(prefs.getString (getAndroidContext ().getString (
                    R.string.alarmRepeatDelayConfigurationKey), "600"))
                * 1000;

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
                    Log.d(getClass().toString(),
                            contact.getName () + " has responded to alarm ("
                            + contact.getPhone () + ").");
                    helpConfirmed = true;
                    break;
                }
            }
        }

        if (helpConfirmed) {
            cancel ();
            Intent message = new Intent (ServiceMessage.UI_MESSAGE);
            message.putExtra (ServiceMessage.Key.RESCUER_ID, contact.getId ());
            message.putExtra (ServiceMessage.Key.RESCUER_NAME, contact.getName());
            getAlarmContext ().getAndroidContext ().sendBroadcast (message);

            getAlarmContext ().setNext (new ConfirmedState (contact));
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
