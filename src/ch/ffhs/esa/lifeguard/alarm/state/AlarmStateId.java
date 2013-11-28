package ch.ffhs.esa.lifeguard.alarm.state;

/**
 * All possible states.
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 */
public enum AlarmStateId {
  INIT,
  TICKING,
  ALARMING,
  AWAITING,
  CONFIRMED;
}
