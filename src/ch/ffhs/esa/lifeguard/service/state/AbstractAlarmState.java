package ch.ffhs.esa.lifeguard.service.state;

import ch.ffhs.esa.lifeguard.service.context.AlarmContext;

public abstract class AbstractAlarmState
    implements AlarmState
{
    private AlarmContext context;

    public AbstractAlarmState setContext (AlarmContext context)
    {
        this.context = context;
        return this;
    }

    public AlarmContext getContext ()
    { return context; }

    @Override
    public void process (AlarmContext context)
    { setContext (context).doProcess (); }

    protected abstract void doProcess ();
}
