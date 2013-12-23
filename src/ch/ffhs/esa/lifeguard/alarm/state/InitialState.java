package ch.ffhs.esa.lifeguard.alarm.state;

import ch.ffhs.esa.lifeguard.alarm.ServiceMessage;
import android.content.Intent;

/**
 * Represents the initial state of the service.
 *
 * @author David Daniel <david.daniel@students.ffhs.ch>
 */
public class InitialState
    extends AbstractAlarmState
{
    private AlarmStateId previous;
    private boolean cancelled = false;

    /*//////////////////////////////////////////////////////////////////////////
     * PUBLIC INTERFACE
     */

    public InitialState ()
    {
        this (AlarmStateId.INIT, false);
    }

    public InitialState (AlarmStateId previous)
    {
        this (previous, false);
    }

    public InitialState (AlarmStateId previous, boolean cancelled)
    {
        this.previous = previous;
        this.cancelled = cancelled;
    }

    @Override
    public AlarmStateId getId ()
    {
        return AlarmStateId.INIT;
    }


    /*//////////////////////////////////////////////////////////////////////////
     * PROTECTED OPERATIONS
     */

    @Override
    protected void start ()
    {}


    @Override
    public void putStateInfo (Intent intent)
    {
        intent.putExtra (
                ServiceMessage.Key.PREVIOUS_ALARM_STATE_ID, previous.toString ());
        intent.putExtra (ServiceMessage.Key.WAS_CANCELLED, cancelled);
    }
}
