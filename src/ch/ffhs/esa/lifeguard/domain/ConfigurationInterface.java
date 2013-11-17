package ch.ffhs.esa.lifeguard.domain;

import ch.ffhs.esa.lifeguard.persistence.Persistable;

/**
 * Defines an interface for the Configuration data type.
 * 
 * @author Juerg Gutknecht <juerg.gutknecht@students.ffhs.ch>
 *
 */
public interface ConfigurationInterface extends Persistable {

	/**
	 * Retrieves the delay in seconds with which the alarm is raised
	 */
	public int getDelay();
	
	/**
	 * Sets the delay in seconds with which the alarm is raised
	 * 
	 * @param delay The delay to set (in [s])
	 */
	public ConfigurationInterface setDelay(final int delay);
}
