package ch.ffhs.esa.lifeguard.alarm.state;

import android.content.Intent;
import ch.ffhs.esa.lifeguard.alarm.ServiceMessage;

/**
 * Handles the confirmation of the rescuers reply.
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 */
public class ConfirmedState
    extends AbstractAlarmState
{
    /*//////////////////////////////////////////////////////////////////////////
     * PUBLIC INTERFACE
     */

    // public ConfirmedState (String rescuerNumber)


    @Override
    public AlarmStateId getId ()
    {
        return AlarmStateId.CONFIRMED;
    }

    /*//////////////////////////////////////////////////////////////////////////
     * PROTECTED OPERATIONS
     */

    @Override
    protected void start () {
        // TODO Auto-generated method stub
    }

    @Override
    public void getStateInfo (Intent intent)
    {
        // TODO Auto-generated method stub
    }
}
