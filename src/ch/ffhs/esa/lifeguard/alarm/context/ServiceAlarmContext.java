package ch.ffhs.esa.lifeguard.alarm.context;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import ch.ffhs.esa.lifeguard.alarm.ServiceMessage;
import ch.ffhs.esa.lifeguard.alarm.state.AlarmState;
import ch.ffhs.esa.lifeguard.alarm.state.AlarmStateListener;
import ch.ffhs.esa.lifeguard.alarm.state.InitialState;

/**
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 *
 */
public class ServiceAlarmContext
    implements AlarmContext
{
    
	/*//////////////////////////////////////////////////////////////////////////
	 * PROPERTIES
	 */
	
	private ArrayList<AlarmStateListener> listeners
        = new ArrayList<AlarmStateListener> ();

    private AlarmState current = null;

    private Context baseContext;

    
    /*//////////////////////////////////////////////////////////////////////////
	 * PUBLIC INTERFACE
	 */

    public ServiceAlarmContext (Context baseContext)
    {
        this.baseContext = baseContext;
        current = new InitialState ();
    }

    @Override
    public Context getBaseContext ()
    {
        return baseContext;
    }
    
    private void sendStatus (Intent extraIntent)
    {
        Intent intent = new Intent (ServiceMessage.CURRENT_SERVICE_STATE);
        if (extraIntent != null) {
            intent.putExtras(extraIntent.getExtras());
        }
        intent.putExtra ("stateId", getState().getId().toString ());
        getBaseContext ().sendBroadcast (intent);
    }

    @Override
    public void setNext (AlarmState state)
    {
        setNext(state, null);
    }
    
    public void setNext (AlarmState state, Intent intent)
    {
        current = state;
        //notifyListeners ();
        sendStatus(intent);
        current.process (this);
    }
    

    @Override
    public AlarmState getState ()
    {
    	return current;
    }

    @Override
    public void addListener (AlarmStateListener listener)
    {
    	listeners.add (listener);
    }

    @Override
    public void removeListener (AlarmStateListener listener)
    {
        listeners.remove (listener);
    }

    @Override
    public void cancel ()
    {
        current.cancel ();
        current = new InitialState ();
    }
}
