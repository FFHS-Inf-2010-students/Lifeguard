package ch.ffhs.esa.lifeguard.alarm.state;

import java.util.ArrayList;
import ch.ffhs.esa.lifeguard.Lifeguard;
import ch.ffhs.esa.lifeguard.alarm.SmsDeliveredReceiver;
import ch.ffhs.esa.lifeguard.alarm.SmsSentReceiver;
import ch.ffhs.esa.lifeguard.domain.Contact;
import ch.ffhs.esa.lifeguard.domain.Contacts;
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
    private String myName = "Hans Wurst";
    private String alarmMessage = myName + " needs help!";
    private Object[]  contactList;
    private int contactPosition;

    /*//////////////////////////////////////////////////////////////////////////
	 * PUBLIC INTERFACE
	 */
    
    public AlarmingState() {
        super();
        contactList = new Contacts(Lifeguard.getDatabaseHelper()).getAll().toArray();
        contactPosition = -1;
    }
	
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
        Log.d(this.getClass().toString(), "doProcess ALarmingState");
        try {
            Contact recipient = getNextContact();
            Log.d(this.getClass().toString(), "Try to notify " + recipient.getName()
                    + " (" + recipient.getPhone() + ")" );
            notifyRecipient(recipient);
        } catch (ArrayIndexOutOfBoundsException e) {
            // TODO maybe inform listeners
            Log.d(this.getClass().toString(), "All contacts already alarmed" + e);
        }
    }
    

    private Contact getNextContact() throws ArrayIndexOutOfBoundsException {
        contactPosition++;
        return (Contact) contactList[contactPosition];
    }
    
    public Contact getLastNotifiedContact() {
        return (Contact) contactList[contactPosition];
    }
    
    
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