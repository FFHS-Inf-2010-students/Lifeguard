package ch.ffhs.esa.lifeguard.alarm;

import ch.ffhs.esa.lifeguard.alarm.context.AlarmContext;
import ch.ffhs.esa.lifeguard.alarm.context.ServiceAlarmContext;
import ch.ffhs.esa.lifeguard.alarm.state.AlarmState;
import ch.ffhs.esa.lifeguard.alarm.state.AlarmStateListener;
import ch.ffhs.esa.lifeguard.alarm.state.AlarmingState;
import ch.ffhs.esa.lifeguard.alarm.state.AwaitingState;
import ch.ffhs.esa.lifeguard.alarm.state.InitialState;
import ch.ffhs.esa.lifeguard.domain.Contact;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 * @author Thomas Aregger <thomas.aregger@students.ffhs.ch>
 *
 */
public class AlarmService extends Service implements AlarmStateListener {
    private final IBinder binder = new AlarmBinder();
    private Contact lastNotifiedContact;
    
    
    /*//////////////////////////////////////////////////////////////////////////
     * PROPERTIES
     */
    
    private AlarmContext alarmContext = new ServiceAlarmContext ();
    
    
    /*//////////////////////////////////////////////////////////////////////////
     * PUBLIC INTERFACE
     */
    
    @Override
    public void onCreate ()
    {
        Log.d(AlarmService.class.toString(), "Service Started");
        alarmContext.setNext(new InitialState());
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
        context.getState ().process (context, getBaseContext());
    }
    
    @Override
    public IBinder onBind (Intent intent) {
        return binder;
    }
    
    public AlarmState getState() {
        return alarmContext.getState();
    }
    
    public void doManualAlarm() {
        if ( isAlarmingState() ) {
            Log.d(this.getClass().toString(), "Already alarmed.");
        } else {
            alarmContext.setNext(new AlarmingState());
            alarmContext.getState().process(alarmContext, getBaseContext());
            lastNotifiedContact = ((AlarmingState) alarmContext.getState()).getLastNotifiedContact();
            alarmContext.setNext(new AwaitingState());
            alarmContext.getState().process(alarmContext, getBaseContext());
        }
       
    }
    
    public String getAlarmButtonMsg() {
        if ( alarmContext.getState().getClass() == AlarmingState.class ) {
            return lastNotifiedContact.getName() + " wurde alarmiert.";
        } else if (alarmContext.getState().getClass() == AwaitingState.class ) {
            return lastNotifiedContact.getName() + " wurde bereits alarmiert.";
        } else {
            return "Halten Sie den Knopf für 5 Sekunden gedrückt um sofort einen Alarm auszulösen.";
        }
        
    }
    
    private boolean isAlarmingState() {
        if ( alarmContext.getState().getClass() == AlarmingState.class
                || alarmContext.getState().getClass() == AwaitingState.class) {
            return true;
        } else {
            return false;
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
