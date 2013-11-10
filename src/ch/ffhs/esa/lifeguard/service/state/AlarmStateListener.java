package ch.ffhs.esa.lifeguard.service.state;

import ch.ffhs.esa.lifeguard.service.context.AlarmContext;

/**
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 *
 */
public interface AlarmStateListener
{
    public void onStateChanged (AlarmContext context);
}
