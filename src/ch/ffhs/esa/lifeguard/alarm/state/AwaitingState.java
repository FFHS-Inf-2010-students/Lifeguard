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
 * @author David Daniel <david.daniel@students.ffhs.ch>
 */
public class AwaitingState extends AbstractAlarmState
{
    private class ReplyReceiver
        extends BroadcastReceiver
    {
        @Override
        public void onReceive (Context context, Intent intent)
        { receiveReply (context, intent); }
    }

    private ReplyReceiver replyReceiver = new ReplyReceiver ();

    private String rescueNumber;

    private int contactIndex;

    private Timer timer;

    /*
     * //////////////////////////////////////////////////////////////////////////
     * PUBLIC INTERFACE
     */

    public AwaitingState (String rescueNumber, int contactIndex)
    {
        this.rescueNumber = rescueNumber;
        this.contactIndex = contactIndex;
    }

    @Override
    public AlarmStateId getId ()
    {
        return AlarmStateId.AWAITING;
    }

    public void cancel ()
    {
        Context base = getContext ().getBaseContext ();
        base.unregisterReceiver (replyReceiver);
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

    protected void registerReceiver ()
    {
        IntentFilter filter = new IntentFilter ("android.provider.Telephony.SMS_RECEIVED");
        getContext ().getBaseContext ().registerReceiver (replyReceiver, filter);
    }

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
                intent.putExtra ("receiver", rescueNumber);
                getContext ().getBaseContext ().sendBroadcast (intent);
                getContext ().setNext (new AlarmingState (contactIndex));
            }
        };
        /* TODO use a valid delay or calculate date */
        long delay = 1000000;
        timer.schedule (timeout, delay);
    }

    protected void receiveReply (Context context, Intent intent)
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

                if (senderNumber.equals (rescueNumber)) {
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
