package ch.ffhs.esa.lifeguard.alarm.state;

import java.util.Timer;
import java.util.TimerTask;

import ch.ffhs.esa.lifeguard.Lifeguard;
import ch.ffhs.esa.lifeguard.alarm.ServiceMessage;
import android.content.Intent;

/**
 * This state is active when the user activity has to be watched.
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 */
public class TickingState
    extends AbstractAlarmState
{
    private Timer timer = null;

    private Long clockTick = 0L;

    private Long maxClockTick;

    private TimerTask task = new TimerTask() {
        @Override
        public void run ()
        { tick (); }
    };

    /*//////////////////////////////////////////////////////////////////////////
     * PUBLIC INTERFACE
     */

    @Override
    public AlarmStateId getId ()
    {
        return AlarmStateId.TICKING;
    }


    /*//////////////////////////////////////////////////////////////////////////
     * PROTECTED OPERATIONS
     */

    @Override
    protected void start ()
    {
        //default timeout 10min
        cancel ();
        maxClockTick = Long.parseLong(
                getAndroidContext ().getSharedPreferences (Lifeguard.APPLICATION_SETTINGS, 0)
                    .getString ("alarmDelay", "600")) * 1000;
        clockTick = 0L;
        timer = new Timer (true);
        timer.scheduleAtFixedRate (task, 1000L, 1000L);
        // TODO Create and start a motion listener that resets the counter
    }

    @Override
    public void cancel ()
    {
        if (timer != null) {
            timer.cancel ();
            timer = null;
        }
    }

    @Override
    public void putStateInfo (Intent intent)
    {
        intent.putExtra ("clockTick", clockTick);
        intent.putExtra ("maxClockTick", maxClockTick);
    }

    private void tick ()
    {
        ++clockTick;

        Intent intent = new Intent (ServiceMessage.ALARM_CLOCK_TICK);
        putStateInfo (intent);
        getAndroidContext ().sendBroadcast (intent);

        if (clockTick.compareTo (maxClockTick) >= 0) {
            cancel ();
            getAlarmContext ().setNext (new AlarmingState ());
        }
    }
}
