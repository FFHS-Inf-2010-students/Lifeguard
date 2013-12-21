package ch.ffhs.esa.lifeguard;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;
import ch.ffhs.esa.lifeguard.domain.Contacts;
import ch.ffhs.esa.lifeguard.persistence.DatabaseHelper;
import ch.ffhs.esa.lifeguard.persistence.TableGatewayInterface;

/**
 * Lifeguard application object
 * 
 * @author Juerg Gutknecht <juerg.gutknecht@students.ffhs.ch>
 *
 */
public class Lifeguard extends Application {
	
	/*//////////////////////////////////////////////////////////////////////////
	 * PROPERTIES
	 */
	
	private static SQLiteOpenHelper databaseHelper;
	
	public static final String APPLICATION_SETTINGS = "APPLICATION_SETTINGS";
	
	@SuppressWarnings("rawtypes")
    private static List<TableGatewayInterface> gateways;
	
	
	/*//////////////////////////////////////////////////////////////////////////
	 * INITIALIZATION
	 */
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		databaseHelper = new DatabaseHelper(this);
	}
	
	
	/*//////////////////////////////////////////////////////////////////////////
	 * PUBLIC INTERFACE
	 */
	
	/**
	 * Returns the application-wide database helper
	 * 
	 * @return The database helper
	 */
	public static SQLiteOpenHelper getDatabaseHelper() {
		return databaseHelper;
	}
	
	/**
	 * Sets a new application-wide database helper
	 * 
	 * @param helper The new database helper to set
	 */
	public static void setDatabaseHelper(SQLiteOpenHelper helper) {
	    databaseHelper = helper;
	}
	
	/**
	 * Returns a list of all registered table gateways
	 * 
	 * @return The list of registerd gateways
	 */
	@SuppressWarnings("rawtypes")
	public static List<TableGatewayInterface> getTableGateways() {
		if (null == gateways) {
		    gateways = new ArrayList<TableGatewayInterface>();
		    gateways.add(new Contacts(databaseHelper));
		}
		
		return gateways;
	}
	
	public String getSharedPreferencesIdentifier() {
	    return APPLICATION_SETTINGS;
	}
}
