package ch.ffhs.esa.lifeguard.service.state;

public class TimingState
    extends AbstractAlarmState
{
    @Override
    protected void doProcess ()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public AlarmStateId getId ()
    { return AlarmStateId.TICKING; }
}
