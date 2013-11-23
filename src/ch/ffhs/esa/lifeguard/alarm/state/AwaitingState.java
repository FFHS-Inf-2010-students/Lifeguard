package ch.ffhs.esa.lifeguard.alarm.state;

/**
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 *
 */
public class AwaitingState
    extends AbstractAlarmState
{
    
	/*//////////////////////////////////////////////////////////////////////////
	 * PUBLIC INTERFACE
	 */
	
	@Override
    public AlarmStateId getId ()
    {
		return AlarmStateId.AWAITING;
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
