package ch.ffhs.esa.lifeguard.alarm.state;

import android.content.Intent;
import ch.ffhs.esa.lifeguard.alarm.context.AlarmContext;

/**
 * Handles the current state of the background service.
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 */
public interface AlarmState
{
    /**
     * Starts this state, sets it to be active.
     * @param alarmContext the alarm context
     */
    public void start (AlarmContext alarmContext);

    /**
     * Cancels this state, aborts its processing.
     */
    public void cancel ();

    /**
     * Returns the id of this state.
     * @return the id of this state.
     */
    public AlarmStateId getId ();

    /**
     * Puts the representation of this state into the given intent.
     * @param intent the intent to inform about this state
     */
    public void putStateInfo (Intent intent);
}
