package ch.ffhs.esa.lifeguard.alarm.state;

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
	
	private AlarmContext context;

	
	/*//////////////////////////////////////////////////////////////////////////
	 * PUBLIC INTERFACE
	 */
	
    public AbstractAlarmState setContext (AlarmContext context)
    {
        this.context = context;
        return this;
    }

    public AlarmContext getContext ()
    {
    	return context;
    }

    @Override
    public void process (AlarmContext context)
    {
    	setContext (context).doProcess ();
    }

    
    /*//////////////////////////////////////////////////////////////////////////
	 * PROTECTED OPERATIONS
	 */
    
    protected abstract void doProcess ();
}
