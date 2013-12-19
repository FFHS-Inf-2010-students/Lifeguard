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
import ch.ffhs.esa.lifeguard.alarm.state.AlarmingState;
import ch.ffhs.esa.lifeguard.alarm.state.InitialState;

/**
 * The background service of Lifeguard.
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 * @author Thomas Aregger <thomas.aregger@students.ffhs.ch>
 */
public class AlarmService extends Service {
    private final IBinder binder = new AlarmBinder();
//    private Contact lastNotifiedContact;
    
    
    /*//////////////////////////////////////////////////////////////////////////
     * PROPERTIES
     */
    
    private AlarmContext alarmContext = new ServiceAlarmContext (this);

    private BroadcastReceiver cancelReceiver = new BroadcastReceiver () {
        public void onReceive (Context context, Intent intent) {
            onManualCancel ();
        }
    };

    private BroadcastReceiver alarmReceiver = new BroadcastReceiver () {
        public void onReceive (Context context, Intent intent) {
            onManualAlarm ();
        }
    };
    
    
    /*//////////////////////////////////////////////////////////////////////////
     * PUBLIC INTERFACE
     */
    
    @Override
    public void onCreate ()
    {
        super.onCreate ();
        Log.d(AlarmService.class.toString(), "Service created");

        alarmContext.setNext (new InitialState ());

        registerReceiver (
                alarmReceiver,
                new IntentFilter (ActivityMessage.MANUAL_ALARM));
        registerReceiver (
                cancelReceiver,
                new IntentFilter (ActivityMessage.CANCEL_OPERATION));
    }

    public void onStart ()
    {
        Log.d(AlarmService.class.toString(), "Service on start");
    }

    @Override
    public IBinder onBind (Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy ()
    {
        unregisterReceiver (alarmReceiver);
        unregisterReceiver (cancelReceiver);
        super.onDestroy ();
    }

    public class AlarmBinder extends Binder {
        public AlarmService getService() {
            return AlarmService.this;
        }
    }

    private void onManualCancel ()
    {
        alarmContext.cancel ();
    }

    private void onManualAlarm ()
    {
        alarmContext.cancel ();
        alarmContext.setNext (new AlarmingState ());
    }
}
