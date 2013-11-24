package ch.ffhs.esa.lifeguard.alarm.state;

import android.content.Context;
import ch.ffhs.esa.lifeguard.alarm.context.AlarmContext;

/**
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 *
 */
public interface AlarmState
{
	public void process (AlarmContext alarmContext, Context serviceContext);

    public AlarmStateId getId ();
}
