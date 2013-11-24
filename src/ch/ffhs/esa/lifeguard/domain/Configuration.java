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
	private static final int DEFAULT_DELAY = 60;
	private int delay = DEFAULT_DELAY;

	/*//////////////////////////////////////////////////////////////////////////
	 * PUBLIC INTERFACE
	 */
	
	@Override
	public String toString() {
		return "" + delay;
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
		return delay;
	}

	@Override
	public ConfigurationInterface setDelay(int delay) {
		if (delay == 0) this.delay = DEFAULT_DELAY;
		else this.delay = delay;
		return this;
	}

}
