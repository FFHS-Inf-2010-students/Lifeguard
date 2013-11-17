package ch.ffhs.esa.lifeguard.persistence;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Juerg Gutknecht <juerg.gutknecht@students.ffhs.ch>
 * @param <E>
 *
 */
public abstract class TableGateway {

	/*//////////////////////////////////////////////////////////////////////////
	 * CLASS CONSTANTS
	 */
	
	protected static final int COLUMN_INDEX_ID = 0;
	
	protected static final String COLUMN_ID = "_id";
	
	
	/*//////////////////////////////////////////////////////////////////////////
	 * PROPERTIES
	 */

	private SQLiteOpenHelper helper;
	
	private String table;
	
	
	/*//////////////////////////////////////////////////////////////////////////
	 * INITIALIZATION
	 */
	
	/**
	 * Table gateway constructor.
	 * 
	 * @param helper The database helper
	 * @param table The table name
	 */
	public TableGateway(SQLiteOpenHelper helper, final String table) {
		this.helper = helper;
		this.table = table;
	}
	
	
	/*//////////////////////////////////////////////////////////////////////////
	 * PROTECTED OPERATIONS
	 */
	
	protected SQLiteDatabase getReadableDatabase() {
		return this.helper.getReadableDatabase();
	}
	
	protected SQLiteDatabase getWritableDatabase() {
		return this.helper.getWritableDatabase();
	}
	
	protected void closeDatabase() {
		this.helper.close();
	}
	
	protected String getTable() {
		return this.table;
	}
	
	protected long getLastInsertId() {
		String query = "SELECT ROWID FROM "
					 + this.getTable()
					 + " ORDER BY ROWID DESC LIMIT 1";
		
		Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
		
		if (null != cursor && cursor.moveToFirst()) {
			return cursor.getLong(0);
		}
		
		return -1L;
	}
}
