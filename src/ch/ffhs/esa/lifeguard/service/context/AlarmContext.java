package ch.ffhs.esa.lifeguard.service.context;

import ch.ffhs.esa.lifeguard.service.state.AlarmState;
import ch.ffhs.esa.lifeguard.service.state.AlarmStateListener;

public interface AlarmContext
{
    public void setNext (AlarmState state);

    public AlarmState getState ();

    public void addListener (AlarmStateListener listener);
}
