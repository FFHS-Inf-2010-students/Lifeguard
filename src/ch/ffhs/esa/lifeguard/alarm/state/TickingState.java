package ch.ffhs.esa.lifeguard.alarm.state;

import android.content.Intent;

/**
 * This state is active when the user activity has to be watched.
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 */
public class TickingState
    extends AbstractAlarmState
{

    /*//////////////////////////////////////////////////////////////////////////
     * PUBLIC INTERFACE
     */

    @Override
    public AlarmStateId getId ()
    {
        return AlarmStateId.TICKING;
    }


    /*//////////////////////////////////////////////////////////////////////////
     * PROTECTED OPERATIONS
     */

    @Override
    protected void start ()
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void getStateInfo (Intent intent)
    {
        // TODO Auto-generated method stub
    }
}
