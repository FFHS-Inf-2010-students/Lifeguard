package ch.ffhs.esa.lifeguard.alarm;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import ch.ffhs.esa.lifeguard.ActivityMessage;
import ch.ffhs.esa.lifeguard.alarm.context.AlarmContext;
import ch.ffhs.esa.lifeguard.alarm.context.ServiceAlarmContext;
import ch.ffhs.esa.lifeguard.alarm.state.AlarmState;
import ch.ffhs.esa.lifeguard.alarm.state.AlarmStateId;
import ch.ffhs.esa.lifeguard.alarm.state.AlarmStateListener;
import ch.ffhs.esa.lifeguard.alarm.state.AlarmingState;
import ch.ffhs.esa.lifeguard.alarm.state.InitialState;
import ch.ffhs.esa.lifeguard.alarm.state.TickingState;

/**
 * The background service of Lifeguard.
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 * @author Thomas Aregger <thomas.aregger@students.ffhs.ch>
 */
public class AlarmService extends Service implements AlarmStateListener {
    private final IBinder binder = new AlarmBinder();
//    private Contact lastNotifiedContact;
    
    
    /*//////////////////////////////////////////////////////////////////////////
     * PROPERTIES
     */
    
    private AlarmContext alarmContext = new ServiceAlarmContext (this);

    private BroadcastReceiver cancelReceiver = new BroadcastReceiver () {
        public void onReceive (Context context, Intent intent) {
            handleManualCancel (intent);
        }
    };

    private BroadcastReceiver stateChangeRequestListener = new BroadcastReceiver () {
        public void onReceive (Context context, Intent intent) {
            handleStateChangeRequest (intent);
        }
    };

    /*//////////////////////////////////////////////////////////////////////////
     * PUBLIC INTERFACE
     */

    public AlarmService ()
    {
        super ();
        alarmContext.addListener (this);
    }
    
    public AlarmContext getContext() {
        return alarmContext;
    }
    
    @Override
    public void onCreate ()
    {
        super.onCreate ();
        Log.d(AlarmService.class.toString(), "Service created");

        registerReceiver (
                stateChangeRequestListener,
                new IntentFilter (ActivityMessage.STATE_CHANGE_REQUEST));
        registerReceiver (
                cancelReceiver,
                new IntentFilter (ActivityMessage.CANCEL_OPERATION));
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId)
    {
        Log.d(AlarmService.class.toString(), "Service on start");
        sendStatus ();
        return START_STICKY;
    }

    @Override
    public void onStateChanged (AlarmContext context)
    {
        sendStatus ();
    }
    
    @Override
    public IBinder onBind (Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy ()
    {
        unregisterReceiver (stateChangeRequestListener);
        unregisterReceiver (cancelReceiver);
        super.onDestroy ();
    }

    
    /*//////////////////////////////////////////////////////////////////////////
     * PRIVATE OPERATIONS
     */

    private void sendStatus ()
    {
        Intent intent = new Intent (ServiceMessage.CURRENT_SERVICE_STATE);
        AlarmState state = alarmContext.getState ();

        intent.putExtra (
                ServiceMessage.Key.ALARM_STATE_ID,
                state.getId ().toString ());
        state.putStateInfo (intent);

        sendBroadcast (intent);
    }

    public class AlarmBinder extends Binder {
        public AlarmService getService() {
            return AlarmService.this;
        }
    }

    private void handleManualCancel (Intent intent)
    {
        alarmContext.cancel ();
    }

    private void handleStateChangeRequest (Intent intent)
    {
        AlarmStateId id = AlarmStateId.valueOf (AlarmStateId.class,
                intent.getExtras ().getString (ActivityMessage.Key.ALARM_STATE_ID));
        
        AlarmState state = null;

        switch (id) {
        case ALARMING:
            state = new AlarmingState ();
            break;
        case TICKING:
            state = new TickingState ();
            break;
        case INIT:
            state = new InitialState ();
            break;
        default:
        case AWAITING: // not allowed
        case CONFIRMED: // not allowed
            break;
        }

        if (state != null) {
            alarmContext.getState ().cancel ();
            alarmContext.setNext (state);
        }
    }
}
