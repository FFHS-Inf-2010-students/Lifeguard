package ch.ffhs.esa.lifeguard.service;

import ch.ffhs.esa.lifeguard.service.context.AlarmContext;
import ch.ffhs.esa.lifeguard.service.context.ServiceAlarmContext;
import ch.ffhs.esa.lifeguard.service.state.AlarmStateListener;
import ch.ffhs.esa.lifeguard.service.state.InitialState;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 *
 */
public class AlarmService
    extends Service
    implements AlarmStateListener
{
    
	/*//////////////////////////////////////////////////////////////////////////
	 * PROPERTIES
	 */
	
	private AlarmContext context
        = new ServiceAlarmContext ();

	
	/*//////////////////////////////////////////////////////////////////////////
	 * PUBLIC INTERFACE
	 */
	
	@Override
    public void onCreate ()
    {
        context.setNext (new InitialState ());
        context.addListener (this);
    }

    public void onStart ()
    {
    	sendStatus ();
	}

    @Override
    public void onStateChanged (AlarmContext context)
    {
        sendStatus ();
        context.getState ().process (context);
    }
    
    @Override
    public IBinder onBind (Intent intent)
    {
    	return null;
    }
    
    
    /*//////////////////////////////////////////////////////////////////////////
	 * PRIVATE OPERATIONS
	 */

    private void sendStatus ()
    {
        // @todo Send status via broadcast
    }
}
