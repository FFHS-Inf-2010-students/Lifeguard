package ch.ffhs.esa.lifeguard.alarm.state;

import android.content.Context;
import ch.ffhs.esa.lifeguard.alarm.context.AlarmContext;

/**
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 *
 */
public abstract class AbstractAlarmState
    implements AlarmState
{
    
	/*//////////////////////////////////////////////////////////////////////////
	 * PROPERTIES
	 */
	
	private AlarmContext alarmContext;
	protected Context serviceContext;

	
	/*//////////////////////////////////////////////////////////////////////////
	 * PUBLIC INTERFACE
	 */
	
    public AbstractAlarmState setContext (AlarmContext context)
    {
        this.alarmContext = context;
        return this;
    }

    public AlarmContext getContext ()
    {
    	return alarmContext;
    }

    @Override
    public void process (AlarmContext alarmContext, Context serviceContext)
    {
        this.serviceContext = serviceContext;
    	setContext (alarmContext).doProcess ();
    }

    
    /*//////////////////////////////////////////////////////////////////////////
	 * PROTECTED OPERATIONS
	 */
    
    protected abstract void doProcess ();
}
