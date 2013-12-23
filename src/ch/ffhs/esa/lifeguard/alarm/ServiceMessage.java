package ch.ffhs.esa.lifeguard.alarm;

/** All messages emitted by the state machine. */
public abstract class ServiceMessage
{
    /** Emitted on every state change */
    public static final String CURRENT_SERVICE_STATE
        = "ch.ffhs.esa.lifeguard.alarm.ServiceMessage.CURRENT_SERVICE_STATE";

    /** Emitted on every clock tick (every second) in TickingState */
    public static final String ALARM_CLOCK_TICK
        = "ch.ffhs.esa.lifeguard.alarm.ServiceMessage.ALARM_CLOCK_TICK";

    /** Specific elements within the messages. */
    public static abstract class Key
    {
        /**
         * The AlarmStateId as a string (use getString to retrieve).
         * Provided by all CURRENT_SERVICE_STATE messages.
         */
        public static final String ALARM_STATE_ID = "alarmStateId";

        /**
         * The id of the correspondant contact as long (use getLong to retrieve).
         * Provided the CURRENT_SERVICE_STATE message on the following states:
         * - AwaitingState
         * - ConfirmedState
         */
        public static final String CONTACT_ID = "contactId";

        /**
         * The position of the contact on the contact list as long (use getLong to retrieve).
         * Provided the CURRENT_SERVICE_STATE message on the AlarmingState.
         */
        public static final String CONTACT_POSITION = "contactPosition";

        /** Provided by the ALARM_CLOCK_TICK message, the current tick value as long */
        public static final String CLOCK_TICK = "clockTick";

        /** Provided by the ALARM_CLOCK_TICK message, the threshold as long */
        public static final String MAX_CLOCK_TICK = "maxClockTick";

        /** Provided by the CURRENT_SERVICE_STATE message on initial state */
        public static final String PREVIOUS_ALARM_STATE_ID = "previousAlarmStateId";

        /** Provided by the CURRENT_SERVICE_STATE message on initial state */
        public static final String WAS_CANCELLED = "wasPreviousCancelled";
    }
}
