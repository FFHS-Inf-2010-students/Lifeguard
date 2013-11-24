package ch.ffhs.esa.lifeguard.alarm.state;

import java.util.ArrayList;

import ch.ffhs.esa.lifeguard.alarm.SmsDeliveredReceiver;
import ch.ffhs.esa.lifeguard.alarm.SmsSentReceiver;
import ch.ffhs.esa.lifeguard.domain.Contact;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 *
 */
public class AlarmingState extends AbstractAlarmState {
    // TODO How to get own name? Make own activity where user can configure his name?
    // (TelephonyManager.TelephonyManager) is not reliable for getting phone number
    String myName = "Hans Wurst";
    String alarmMessage = myName + " needs help!";

    /*//////////////////////////////////////////////////////////////////////////
	 * PUBLIC INTERFACE
	 */
	
	@Override
    public AlarmStateId getId ()
    {
		return AlarmStateId.ALARMING;
    }

	
	/*//////////////////////////////////////////////////////////////////////////
	 * PROTECTED OPERATIONS
	 */
	
    @Override
    protected void doProcess () {
        //TODO Get next contact eligible for alarming (from DB or somewhere)
        notifyRecipient(new Contact("Thomas Aregger", "+41794198461"));
        Log.d(this.getClass().toString(), "doProcess ALarmingState");
    }
    
    /*public void setContext(Context context) {
        this.context = context;
    }*/
    
    private void notifyRecipient(Contact recipient) {
        String phoneNumber = recipient.getPhone();
        
        ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();
        PendingIntent sentPI = PendingIntent.getBroadcast(serviceContext, 0, new Intent(serviceContext, SmsSentReceiver.class), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(serviceContext, 0, new Intent(serviceContext, SmsDeliveredReceiver.class), 0);
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
            Toast.makeText(serviceContext, "SMS sending failed...",Toast.LENGTH_SHORT).show();
        }
    }
}