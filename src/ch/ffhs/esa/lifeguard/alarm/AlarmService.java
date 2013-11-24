package ch.ffhs.esa.lifeguard.alarm;

import ch.ffhs.esa.lifeguard.alarm.context.AlarmContext;
import ch.ffhs.esa.lifeguard.alarm.context.ServiceAlarmContext;
import ch.ffhs.esa.lifeguard.alarm.state.AlarmState;
import ch.ffhs.esa.lifeguard.alarm.state.AlarmStateListener;
import ch.ffhs.esa.lifeguard.alarm.state.AlarmingState;
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
    
    public void doAlarm() {
        alarmContext.setNext(new AlarmingState());
        alarmContext.getState().process(alarmContext, getBaseContext());
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
