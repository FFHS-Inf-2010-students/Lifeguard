package ch.ffhs.esa.lifeguard.domain;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
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
	private static final String COLUMN_DELAY = "delay";
	
	private static final int COLUMN_INDEX_DELAY = 1;
	
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
		Log.d(Configurations.class.toString(), "onCreate...");
		try {
			String query = "CREATE TABLE " + this.getTable()
				     + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT"
				     + ", " + COLUMN_DELAY + " INTEGER NOT NULL"
				     + ");";
			Log.d(Configurations.class.toString(), "Creating table " + this.getTable());
			Log.d(Configurations.class.toString(), query);
			db.execSQL(query);
			Log.d(Configurations.class.toString(), "Table created.");
			
			Log.d(Configurations.class.toString(), "Inserting default data...");
			db.execSQL("INSERT INTO " + this.getTable() + " (delay) VALUES (60);");
			Log.d(Configurations.class.toString(), "Default data inserted.");
		} catch (SQLiteException e) {
			Log.d(Configurations.class.toString(), e.getMessage());
		}
	}		
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(Configurations.class.toString(), "onUpgrade...");
		Log.d(Configurations.class.toString(), "Dropping table " + this.getTable() + " if it exists...");
		db.execSQL("DROP TABLE IF EXISTS " + this.getTable());
		Log.d(Configurations.class.toString(), "Table " + this.getTable() + " dropped.");
		this.onCreate(db);
		
	}
	
	
	/*//////////////////////////////////////////////////////////////////////////
	 * CRUD OPERATIONS
	 */
	
	@Override
	public long persist(ConfigurationInterface object) {
		Log.d(Configurations.class.getName(), "Persisting " + object);
		ContentValues values = new ContentValues();
		values.put(COLUMN_DELAY, object.getDelay());

		long id = -1L;
		
		try {
			if (object.getId() > 0) {
				this.getWritableDatabase().update(
					this.getTable(),
					values,
					COLUMN_ID + " = ?",
					new String[] {String.valueOf(object.getId())}
				);
				id = object.getId();
			} else {
				// TODO not supporting more than one configuration entry 
				// at the moment
				id = this.getWritableDatabase().insert(this.getTable(), null, values);
				object.setId(id);
			}
		} catch (SQLiteException e) {
			Log.e(Contacts.class.getName(), e.getMessage());
		} finally {
			this.closeDatabase();
		}

		return id;
	}

	@Override
	public void delete(ConfigurationInterface object) {
		this.getWritableDatabase().delete(
				this.getTable(),
				"_id = ?",
				new String[] {String.valueOf(object.getId())}
			);
			this.closeDatabase();	
	}

	@Override
	public List<ConfigurationInterface> getAll() {
		// TODO not supporting more than one configuration entry 
		// at the moment
		return null;
	}

	@Override
	public Persistable findById(long id) {
		Cursor cursor = this.getReadableDatabase().rawQuery(
				"SELECT * FROM "
			    + this.getTable()
			    + " WHERE " + COLUMN_ID + " = ? LIMIT 0, 1",
				new String[] {String.valueOf(id)}
			);
			
			ConfigurationInterface configuration;
			
			// TODO do something useful here, when refactoring of 
			// configurations is finished
			if (null != cursor && cursor.moveToFirst()) {
				//configuration = this.createConfiguration(cursor);
				configuration = null;
			} else {
				configuration = new Configuration();
			}
			this.closeDatabase();
			return configuration;
	}

	
}
