package ch.ffhs.esa.lifeguard.alarm.state;

import android.content.Context;
import ch.ffhs.esa.lifeguard.alarm.context.AlarmContext;

/**
 * Base for an alarm state.
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
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
    public void start (AlarmContext alarmContext)
    {
    	setContext (alarmContext).start ();
    }

    @Override
    public void cancel ()
    {}

    /*
     * //////////////////////////////////////////////////////////////////////////
     * PROTECTED OPERATIONS
     */

    protected AbstractAlarmState setContext (AlarmContext context)
    {
        this.alarmContext = context;
        return this;
    }

    protected AlarmContext getAlarmContext ()
    {
    	return alarmContext;
    }

    protected Context getAndroidContext ()
    {
        return alarmContext.getAndroidContext ();
    }

    protected abstract void start ();
}
