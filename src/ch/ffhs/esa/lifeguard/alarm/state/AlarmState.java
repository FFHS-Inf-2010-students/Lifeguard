package ch.ffhs.esa.lifeguard.alarm.state;

import ch.ffhs.esa.lifeguard.alarm.context.AlarmContext;

/**
 * Handles the current state of the background service.
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 */
public interface AlarmState
{
    public void process (AlarmContext alarmContext);

    public void cancel ();

    public AlarmStateId getId ();
}
