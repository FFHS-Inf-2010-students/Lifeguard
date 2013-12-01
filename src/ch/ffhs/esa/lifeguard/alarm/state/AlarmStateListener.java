package ch.ffhs.esa.lifeguard.alarm.state;

import ch.ffhs.esa.lifeguard.alarm.context.AlarmContext;

/**
 * Listens for state shifts.
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 */
public interface AlarmStateListener
{
    public void onStateChanged (AlarmContext context);
}
