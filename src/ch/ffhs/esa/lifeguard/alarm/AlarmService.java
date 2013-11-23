package ch.ffhs.esa.lifeguard.alarm;

import java.util.ArrayList;

import ch.ffhs.esa.lifeguard.MainActivity;
import ch.ffhs.esa.lifeguard.alarm.context.AlarmContext;
import ch.ffhs.esa.lifeguard.alarm.context.ServiceAlarmContext;
import ch.ffhs.esa.lifeguard.alarm.state.AlarmStateId;
import ch.ffhs.esa.lifeguard.alarm.state.AlarmStateListener;
import ch.ffhs.esa.lifeguard.alarm.state.InitialState;
import ch.ffhs.esa.lifeguard.domain.Contact;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 * @author Thomas Aregger <thomas.aregger@students.ffhs.ch>
 *
 */
public class AlarmService extends Service implements AlarmStateListener {
    private AlarmStateId alarmState;
    private final IBinder binder = new AlarmBinder();
    private String alarmMessage;
    
    /*//////////////////////////////////////////////////////////////////////////
     * PROPERTIES
     */
    
    private AlarmContext context = new ServiceAlarmContext ();
    
    
    /*//////////////////////////////////////////////////////////////////////////
     * PUBLIC INTERFACE
     */
    
    @Override
    public void onCreate ()
    {
        /*context.setNext (new InitialState ());
        context.addListener (this);*/
        alarmState = AlarmStateId.INIT;
        Log.d(AlarmService.class.toString(), "Service Started");
        
        // TODO How to get own name? Make own activity where user can configure his name?
        // (TelephonyManager.TelephonyManager) is not reliable for getting phone number
        String myName = "Hans Wurst";
        
        alarmMessage = myName + " needs help!";
        
    }

    public void onStart ()
    {
        Log.d(AlarmService.class.toString(), "On start");
        //sendStatus ();
    }

    @Override
    public void onStateChanged (AlarmContext context)
    {
        sendStatus ();
        context.getState ().process (context);
    }
    
    @Override
    public IBinder onBind (Intent intent) {
        return binder;
    }
    
    public AlarmStateId getState() {
        //return this.alarmState;
        return AlarmStateId.INIT;
    }
    
    public boolean doAlarm() {
        //TODO Get next contact eligible for alarming (from DB or somewhere)
        notifyRecipient(new Contact("Thomas Aregger", "+41794198461"));
        return true;
    }
    
    
    private void notifyRecipient(Contact recipient) {
        String phoneNumber = recipient.getPhone();
        
        ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();
        PendingIntent sentPI = PendingIntent.getBroadcast(getBaseContext(), 0, new Intent(getBaseContext(), SmsSentReceiver.class), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(getBaseContext(), 0, new Intent(getBaseContext(), SmsDeliveredReceiver.class), 0);
        try {
            SmsManager sms = SmsManager.getDefault();
            ArrayList<String> mSMSMessage = sms.divideMessage(alarmMessage);
            for (int i = 0; i < mSMSMessage.size(); i++) {
                sentPendingIntents.add(i, sentPI);
                deliveredPendingIntents.add(i, deliveredPI);
            }
            sms.sendMultipartTextMessage(phoneNumber, null, mSMSMessage,
                    sentPendingIntents, deliveredPendingIntents);

        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(getBaseContext(), "SMS sending failed...",Toast.LENGTH_SHORT).show();
        }

    }
    
    /*//////////////////////////////////////////////////////////////////////////
     * PRIVATE OPERATIONS
     */

    private void sendStatus ()
    {
        // @todo Send status via broadcast
    }
    
    public class AlarmBinder extends Binder {
        public AlarmService getService() {
            return AlarmService.this;
        }
    }
}
