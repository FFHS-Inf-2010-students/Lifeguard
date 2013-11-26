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
    /*
     * //////////////////////////////////////////////////////////////////////////
     * PROPERTIES
     */

    private AlarmContext alarmContext;

    /*
     * //////////////////////////////////////////////////////////////////////////
     * PUBLIC INTERFACE
     */

    @Override
    public void process (AlarmContext alarmContext)
    {
    	setContext (alarmContext).doProcess ();
    }

    /*
     * //////////////////////////////////////////////////////////////////////////
     * PROTECTED OPERATIONS
     */

    protected AbstractAlarmState setContext (AlarmContext context)
    {
        this.alarmContext = context;
        return this;
    }

    protected AlarmContext getContext ()
    {
    	return alarmContext;
    }

    protected Context getServiceContext ()
    {
        return alarmContext.getBaseContext ();
    }

    protected abstract void doProcess ();
}
