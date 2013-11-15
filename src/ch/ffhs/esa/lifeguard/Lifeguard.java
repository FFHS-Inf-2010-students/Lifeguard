package ch.ffhs.esa.lifeguard;

import ch.ffhs.esa.lifeguard.persistence.DatabaseHelper;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
	
	public static SQLiteOpenHelper getDatabaseHelper() {
		return databaseHelper;
	}
	
}
