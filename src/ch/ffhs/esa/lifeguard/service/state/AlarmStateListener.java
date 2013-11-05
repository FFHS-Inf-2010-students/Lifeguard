package ch.ffhs.esa.lifeguard.service.state;

import ch.ffhs.esa.lifeguard.service.context.AlarmContext;

public interface AlarmStateListener
{
    public void onStateChanged (AlarmContext context);
}
