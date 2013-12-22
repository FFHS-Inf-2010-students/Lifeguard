package ch.ffhs.esa.lifeguard.ui;

/**
 * Formats a clock tick to the remaining time.
 */
public class ClockTickFormatter
{
    /**
     * Formats the given clock tick to a representation of hours:minutes:seconds left to go.
     * @param currentTick the current tick value
     * @param maxTick the maximum tick
     * @return the given clock tick as a representation of hours:minutes:seconds left to go
     */
    public static String format (long currentTick, long maxTick)
    {
        long toGo = maxTick - currentTick;

        long minutes = toGo / 60;
        long seconds = toGo % 60;
        long hours = minutes / 60;
        minutes %= 60;

        return String.format ("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
