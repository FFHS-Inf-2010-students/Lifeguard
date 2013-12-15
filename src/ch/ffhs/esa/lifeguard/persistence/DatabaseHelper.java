package ch.ffhs.esa.lifeguard.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ch.ffhs.esa.lifeguard.Lifeguard;

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
	@SuppressWarnings("rawtypes")
	@Override
	public void onCreate(SQLiteDatabase db) {
		for (TableGatewayInterface gateway : Lifeguard.getTableGateways()) {
			gateway.onCreate(db);
		}
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (TableGatewayInterface gateway : Lifeguard.getTableGateways()) {
			gateway.onUpgrade(db, DATABASE_VERSION-1 , DATABASE_VERSION);
		}
	}
}
