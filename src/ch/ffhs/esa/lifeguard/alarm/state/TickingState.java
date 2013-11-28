package ch.ffhs.esa.lifeguard.alarm.state;

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
    protected void doProcess ()
    {
        // TODO Auto-generated method stub

    }
}
