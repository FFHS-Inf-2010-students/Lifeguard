package ch.ffhs.esa.lifeguard.alarm.state;

import android.content.Intent;

/**
 * Represents the initial state of the service.
 *
 * @author David Daniel <david.daniel@students.ffhs.ch>
 */
public class InitialState
    extends AbstractAlarmState
{

    /*//////////////////////////////////////////////////////////////////////////
     * PUBLIC INTERFACE
     */

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
        // no info available / none needed
    }
}
