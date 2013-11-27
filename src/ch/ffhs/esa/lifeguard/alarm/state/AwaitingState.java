package ch.ffhs.esa.lifeguard.alarm.state;

import ch.ffhs.esa.lifeguard.alarm.ServiceMessage;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 * 
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

    /*
     * //////////////////////////////////////////////////////////////////////////
     * PUBLIC INTERFACE
     */

    @Override
    public AlarmStateId getId ()
    {
        return AlarmStateId.AWAITING;
    }

    /*
     * //////////////////////////////////////////////////////////////////////////
     * PROTECTED OPERATIONS
     */

    @Override
    protected void doProcess ()
    { registerReceiver (); }

    protected void registerReceiver ()
    {
        IntentFilter filter = new IntentFilter ("android.provider.Telephony.SMS_RECEIVED");
        getContext ().getBaseContext ().registerReceiver (replyReceiver, filter);
    }

    protected void receiveReply (Context context, Intent intent)
    {
        Bundle bundle = intent.getExtras ();
        boolean alarmConfirmed = false;

        if (bundle != null) {
            //---retrieve the SMS message received---
            Object [] pdus = (Object[]) bundle.get ("pdus");

            for (int i = 0; i < pdus.length; ++i) {
                SmsMessage message = SmsMessage.createFromPdu ((byte []) pdus [i]);
                String senderNumber = message.getOriginatingAddress ();

                /* TODO check whether the number equals the one from the real target */
                if (senderNumber.equals ("0041791234567")) {
                    alarmConfirmed = true;
                    break;
                }
            }
        }

        if (alarmConfirmed) {
            Context base = getContext ().getBaseContext ();
            base.unregisterReceiver (replyReceiver);

            base.sendBroadcast (new Intent (ServiceMessage.ALARM_CONFIRMED));
        }
    }
}
