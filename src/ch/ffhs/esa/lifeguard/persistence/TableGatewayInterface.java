package ch.ffhs.esa.lifeguard.persistence;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;

/**
 * Defines an interface for all table gateways.
 * 
 * @author Juerg Gutknecht <juerg.gutknecht@students.ffhs.ch>
 * @param <E>
 *
 */
public interface TableGatewayInterface<E> {

	/**
	 * Writes an object to the database.
	 * 
	 * If the write was successful, the primary key is returned.
	 * Otherwise, a value of -1 is returned.
	 * 
	 * @param object The object to persist
	 * @return The object identifier
	 */
	public long persist(E object);
	
	/**
	 * Deletes an object from the database
	 * 
	 * @param object The object to delete
	 * @return The number of rows deleted
	 */
	public int delete(E object);
	
	/**
	 * Retrieves all rows from the database table
	 * @return
	 */
	public List<E> getAll();
	
	/**
	 * Finds a row by its identifier
	 * 
	 * @param id The identifier to search by
	 * @return The row, if found
	 */
	public Persistable findById(final long id);
	
	/**
	 * Runs a create statement to create the table in the database
	 * 
	 * @param db Reference to open database
	 */
	public void onCreate(SQLiteDatabase db);
	
	/**
	 * Runs an update statement to update the table from one version to another
	 * 
	 * @param db Reference to open database
	 * @param oldVersion The version to update from
	 * @param newVersion The version to update to
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}
