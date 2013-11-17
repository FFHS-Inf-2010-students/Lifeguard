package ch.ffhs.esa.lifeguard.domain;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ch.ffhs.esa.lifeguard.persistence.Persistable;
import ch.ffhs.esa.lifeguard.persistence.TableGateway;

/**
 * Configurations table gateway implementation
 * 
 * @author Juerg Gutknecht <juerg.gutknecht@students.ffhs.ch>
 *
 */
public class Configurations
	extends TableGateway
	implements ConfigurationsInterface {

	/*//////////////////////////////////////////////////////////////////////////
	 * CLASS CONSTANTS
	 */
	
	
	/*//////////////////////////////////////////////////////////////////////////
	 * INITIALIZATION
	 */
	
	public Configurations(SQLiteOpenHelper helper) {
		super(helper, "configurations");
	}

	
	/*//////////////////////////////////////////////////////////////////////////
	 * TABLE CREATION AND UPDATING
	 */
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	
	/*//////////////////////////////////////////////////////////////////////////
	 * CRUD OPERATIONS
	 */
	
	@Override
	public long persist(ConfigurationInterface object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(ConfigurationInterface object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ConfigurationInterface> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Persistable findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
