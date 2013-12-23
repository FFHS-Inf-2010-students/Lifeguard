package ch.ffhs.esa.lifeguard;

/** Messages emitted by activities. */
public abstract class ActivityMessage
{
    /** Emitted on every explicit state change request */
    public static final String STATE_CHANGE_REQUEST
        = "ch.ffhs.esa.lifeguard.activity.STATE_CHANGE_REQUEST";

    /** Emitted on any cancel request of the current operation (leading back to initial state) */
    public static final String CANCEL_OPERATION
        = "ch.ffhs.esa.lifeguard.activity.CANCEL_OPERATION";

    /** Elements within activity messages */
    public static abstract class Key
    {
        /** The id of the requested alarm state as a string of the according AlarmStateId */
        public static final String ALARM_STATE_ID = "alarmStateId";
    }
}
