package ch.ffhs.esa.lifeguard.alarm;

public abstract class ServiceMessage
{
    public static final String CURRENT_SERVICE_STATE
        = "ch.ffhs.lifeguard.alarm.CURRENT_SERVICE_STATE";

    public static final String RESCUE_CONFIRMED
        = "ch.ffhs.lifeguard.alarm.RESCUE_CONFIRMED";

    public static final String ALARM_REPEATED
        = "ch.ffhs.lifeguard.alarm.ALARM_REPEATED";
}
