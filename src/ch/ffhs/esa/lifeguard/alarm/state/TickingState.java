package ch.ffhs.esa.lifeguard.alarm.state;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;

/**
 * This state is active when the user activity has to be watched.
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 */
public class TickingState
    extends AbstractAlarmState
{
    private Timer timer;

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
        timer = new Timer (true);
        timer.scheduleAtFixedRate (task, 0L, 1000L);
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
        // TODO Put the real clock tick into the intent
//        intent.putExtra ("clockTick", clockTick);
    }

    private void tick ()
    {
        
    }
}
