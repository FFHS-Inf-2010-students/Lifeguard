package ch.ffhs.esa.lifeguard;

public abstract class ActivityMessage
{
    public static final String STATE_CHANGE_REQUEST
        = "ch.ffhs.esa.lifeguard.activity.STATE_CHANGE_REQUEST";

    public static final String CANCEL_OPERATION
        = "ch.ffhs.esa.lifeguard.activity.CANCEL_OPERATION";

    public static abstract class Key
    {
        public static final String ALARM_STATE_ID = "alarmStateId";
    }
}
