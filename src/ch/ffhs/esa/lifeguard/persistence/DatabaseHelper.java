package ch.ffhs.esa.lifeguard.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import ch.ffhs.esa.lifeguard.domain.Contacts;
import ch.ffhs.esa.lifeguard.domain.ContactsInterface;

/**
 * @author Juerg Gutknecht <juerg.gutknecht@students.ffhs.ch>
 *
 */
public class DatabaseHelper
	extends SQLiteOpenHelper {
	
	/*//////////////////////////////////////////////////////////////////////////
	 * CLASS CONSTANTS
	 */
	
	private static final String DATABASE_NAME = "lifeguard.db"; 
	
	private static final int DATABASE_VERSION = 1;
	
	
	/*//////////////////////////////////////////////////////////////////////////
	 * INITIALIZATION
	 */
	
	/**
	 * Constructor.
	 * 
	 * @param ctx
	 */
	public DatabaseHelper(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	
	/*//////////////////////////////////////////////////////////////////////////
	 * PUBLIC INTERFACE
	 */
	
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		ContactsInterface contacts = new Contacts(this);
		contacts.onCreate(db);
		Log.d("DatabaseHelper.onCreate", "db.isOpen: " + db.isOpen());
		Log.d("DatabaseHelper.onCreate", "db.isDbLockedByCurrentThread: " + db.isDbLockedByCurrentThread());
		Log.d("DatabaseHelper.onCreate", "db.isReadOnly: " + db.isReadOnly());
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		ContactsInterface contacts = new Contacts(this);
		contacts.onUpgrade(db, 0, 0);
	}

}
