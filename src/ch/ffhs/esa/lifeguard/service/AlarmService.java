package ch.ffhs.esa.lifeguard.service;

import ch.ffhs.esa.lifeguard.service.context.AlarmContext;
import ch.ffhs.esa.lifeguard.service.context.ServiceAlarmContext;
import ch.ffhs.esa.lifeguard.service.state.AlarmStateListener;
import ch.ffhs.esa.lifeguard.service.state.OnInit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AlarmService
    extends Service
    implements AlarmStateListener
{
    private AlarmContext context
        = new ServiceAlarmContext ();

    public void onCreate ()
    {
        context.setNext (new OnInit ());
        context.addListener (this);
    }

    public void onStart ()
    { sendStatus (); }

    public void onStateChanged (AlarmContext context)
    {
        sendStatus ();
        context.getState ().process (context);
    }

    private void sendStatus ()
    {
    }

    @Override
    public IBinder onBind (Intent intent)
    { return null; }
}
