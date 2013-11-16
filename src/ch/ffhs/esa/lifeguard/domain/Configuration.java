package ch.ffhs.esa.lifeguard.domain;


/**
 * Represents a single configuration
 * 
 * @author Juerg Gutknecht <juerg.gutknecht@students.ffhs.ch>
 *
 */
public class Configuration
	implements ConfigurationInterface {

	/*//////////////////////////////////////////////////////////////////////////
	 * CLASS CONSTANTS
	 */
	
	private static final long serialVersionUID = 345474280532185763L;

	
	/*//////////////////////////////////////////////////////////////////////////
	 * PROPERTIES
	 */
	
	private long id = 0;
	
	
	/*//////////////////////////////////////////////////////////////////////////
	 * PUBLIC INTERFACE
	 */
	
	@Override
	public String toString() {
		// TODO: Reasonable toString implementation
		return "";
	}
	
	/* (non-Javadoc)
	 * @see ch.ffhs.esa.lifeguard.persistence.Persistable#getId()
	 */
	@Override
	public long getId() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see ch.ffhs.esa.lifeguard.persistence.Persistable#setId(long)
	 */
	@Override
	public ConfigurationInterface setId(long id) {
		this.id = id;
		return this;
	}

	@Override
	public int getDelay() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ConfigurationInterface setDelay(int delay) {
		// TODO Auto-generated method stub
		return null;
	}

}
