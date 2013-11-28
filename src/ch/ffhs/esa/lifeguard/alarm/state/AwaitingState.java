package ch.ffhs.esa.lifeguard.alarm.state;

import java.util.Timer;
import java.util.TimerTask;

import ch.ffhs.esa.lifeguard.alarm.ServiceMessage;
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

    private String rescuerNumber;

    private int contactIndex;

    private Timer timer;

    /*
     * //////////////////////////////////////////////////////////////////////////
     * PUBLIC INTERFACE
     */

    /**
     * Creates a new awaiting state with the given phone number and the
     * corresponding contact index.
     * @param rescuerNumber the phone number of the rescuer
     * @param contactIndex the index of the corresponding contact within the
     *                     contact list
     */
    public AwaitingState (String rescuerNumber, int contactIndex)
    {
        this.rescuerNumber = rescuerNumber;
        this.contactIndex = contactIndex;
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
                intent.putExtra ("receiver", rescuerNumber);
                getContext ().getBaseContext ().sendBroadcast (intent);
                getContext ().setNext (new AlarmingState (contactIndex));
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
                String senderNumber = message.getOriginatingAddress ();

                if (senderNumber.equals (rescuerNumber)) {
                    helpConfirmed = true;
                    break;
                }
            }
        }

        if (helpConfirmed) {
            cancel ();
            getContext ().getBaseContext ()
                .sendBroadcast (new Intent (ServiceMessage.RESCUE_CONFIRMED));

            getContext ().setNext (new ConfirmedState ());
        }
    }
}
