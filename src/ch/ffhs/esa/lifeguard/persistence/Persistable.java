package ch.ffhs.esa.lifeguard.persistence;

import java.io.Serializable;

/**
 * Defines an interface for persistable objects.
 * 
 * @author Juerg Gutknecht <juerg.gutknecht@students.ffhs.ch>
 *
 */
public interface Persistable extends Serializable {
	/**
	 * Retrieves the object's identifier
	 * 
	 * @return The object identifier
	 */
	public long getId();
	
	/**
	 * Sets the object's identifier
	 * 
	 * @param id The object identifier
	 * @return Self
	 */
	public Persistable setId(final long id);
}
