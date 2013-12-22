package ch.ffhs.esa.lifeguard.alarm.state;

import java.util.Timer;
import java.util.TimerTask;

import ch.ffhs.esa.lifeguard.Lifeguard;
import ch.ffhs.esa.lifeguard.R;
import ch.ffhs.esa.lifeguard.alarm.ServiceMessage;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
        Context ctx = getAndroidContext ();
        maxClockTick
            = Long.parseLong (
                    ctx.getSharedPreferences (
                            Lifeguard.APPLICATION_SETTINGS, Lifeguard.MODE_PRIVATE)
                .getString (ctx.getString (R.string.alarmDelayConfigurationKey), "600"));

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
        intent.putExtra (ServiceMessage.Key.CLOCK_TICK, clockTick);
        intent.putExtra (ServiceMessage.Key.MAX_CLOCK_TICK, maxClockTick);
    }

    private void tick ()
    {
        ++clockTick;

        Intent intent = new Intent (ServiceMessage.ALARM_CLOCK_TICK);
        putStateInfo (intent);
        getAndroidContext ().sendBroadcast (intent);
        Log.d (TickingState.class.getName (), "Tick: " + clockTick);

        if (clockTick.compareTo (maxClockTick) >= 0) {
            cancel ();
            getAlarmContext ().setNext (new AlarmingState ());
        }
    }
}
