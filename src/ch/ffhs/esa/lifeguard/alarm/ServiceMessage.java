package ch.ffhs.esa.lifeguard.alarm;

/**
 * All service messages.
 */
public abstract class ServiceMessage
{
    public static final String CURRENT_SERVICE_STATE
        = "ch.ffhs.esa.lifeguard.alarm.ServiceMessage.CURRENT_SERVICE_STATE";

    public static final String ALARM_REPEATED
        = "ch.ffhs.esa.lifeguard.alarm.ServiceMessage.ALARM_REPEATED";

    public static final String ALARM_CLOCK_TICK
        = "ch.ffhs.esa.lifeguard.alarm.ServiceMessage.ALARM_CLOCK_TICK";

    public static abstract class Key
    {
        public static final String ALARM_STATE_ID = "alarmStateId";

        public static final String CLOCK_TICK = "clockTick";
        public static final String MAX_CLOCK_TICK = "maxClockTick";

        public static final String CONTACT_ID = "contactId";

        public static final String ALARM_RECEIVER_ID = "alarmReceiverId";
        public static final String RESCUER_ID = "rescuerId";
    }
}
