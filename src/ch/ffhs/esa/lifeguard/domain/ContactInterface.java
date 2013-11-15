package ch.ffhs.esa.lifeguard.domain;

import ch.ffhs.esa.lifeguard.persistence.Persistable;

/**
 * 
 * 
 * @author Juerg Gutknecht <juerg.gutknecht@students.ffhs.ch>
 *
 */
public interface ContactInterface extends Persistable {

	/**
	 * Retrieves the contact's name
	 */
	public String getName();
	
	/**
	 * Sets the contact's name
	 * 
	 * @param name The name to set
	 */
	public ContactInterface setName(final String name);
	
	/**
	 * Retrieves the contact's phone number
	 */
	public String getPhone();

	/**
	 * Sets the contact's phone number
	 * 
	 * @param phone The phone number to set
	 */
	public ContactInterface setPhone(final String phone);
	
	/**
	 * Retrieves the contact's position
	 */
	public int getPosition();
	
	/**
	 * Sets the contact's position
	 * 
	 * @param position The position to set
	 */
	public ContactInterface setPosition(final int position);
}
