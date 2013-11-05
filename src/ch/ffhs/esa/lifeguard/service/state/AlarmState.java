package ch.ffhs.esa.lifeguard.service.state;

import ch.ffhs.esa.lifeguard.service.context.AlarmContext;

public interface AlarmState
{
    public void process (AlarmContext context);

    public AlarmStateId getId ();
}
