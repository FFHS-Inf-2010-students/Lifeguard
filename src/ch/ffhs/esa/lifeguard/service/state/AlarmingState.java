package ch.ffhs.esa.lifeguard.service.state;

public class AlarmingState
    extends AbstractAlarmState
{
    @Override
    public AlarmStateId getId ()
    { return AlarmStateId.ALARMING; }

    @Override
    protected void doProcess ()
    {
        // TODO Auto-generated method stub

    }
}
