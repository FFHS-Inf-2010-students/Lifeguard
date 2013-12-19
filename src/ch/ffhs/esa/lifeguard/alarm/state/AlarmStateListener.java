package ch.ffhs.esa.lifeguard.alarm.state;

import ch.ffhs.esa.lifeguard.alarm.context.AlarmContext;

/**
 * Listens for state shifts.
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 */
//TODO Do we need that a listener, or are can we do it with BroadcastReceivers?
public interface AlarmStateListener
{
    public void onStateChanged (AlarmContext context);
}
