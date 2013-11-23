package ch.ffhs.esa.lifeguard.alarm.state;

import ch.ffhs.esa.lifeguard.alarm.context.AlarmContext;

/**
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 *
 */
public interface AlarmStateListener
{
    public void onStateChanged (AlarmContext context);
}
