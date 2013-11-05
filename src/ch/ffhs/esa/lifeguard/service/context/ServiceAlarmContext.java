package ch.ffhs.esa.lifeguard.service.context;

import java.util.TreeSet;

import ch.ffhs.esa.lifeguard.service.state.AlarmState;
import ch.ffhs.esa.lifeguard.service.state.AlarmStateListener;

public class ServiceAlarmContext
    implements AlarmContext
{
    private TreeSet<AlarmStateListener> listeners
        = new TreeSet<AlarmStateListener> ();

    private AlarmState current = null;

    @Override
    public void setNext (AlarmState state)
    {
        current = state;
        notifyListeners ();
    }

    @Override
    public AlarmState getState ()
    { return current; }

    @Override
    public void addListener (AlarmStateListener listener)
    { listeners.add (listener); }

    protected void notifyListeners ()
    {
        for (AlarmStateListener l : listeners) {
            l.onStateChanged (this);
        }
    }
}
