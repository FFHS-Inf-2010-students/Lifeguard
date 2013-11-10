package ch.ffhs.esa.lifeguard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * The application's main activity (aka home screen).
 * 
 * @author Thomas Aregger <thomas.aregger@students.ffhs.ch>
 * @author Juerg Gutknecht <juerg.gutknecht@students.ffhs.ch>
 *
 */
public class MainActivity extends Activity {

	/*//////////////////////////////////////////////////////////////////////////
	 * CREATION
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	/*//////////////////////////////////////////////////////////////////////////
	 * EVENT HANDLING
	 */
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_configuration:
				openConfiguration();
				return true;
			case R.id.action_contact_list:
				viewContacts();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	
	/*//////////////////////////////////////////////////////////////////////////
	 * ACTIONS
	 */
	
	/**
	 * Starts the configuration activity.
	 */
	public void openConfiguration() {
		Log.d(MainActivity.class.toString(), "Open configuration...");
		Intent intent = new Intent(this, ConfigurationActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Starts the contact list activity to display all available contacts.
	 */
	public void viewContacts() {
		Log.d(MainActivity.class.toString(), "View contacts...");
		Intent intent = new Intent(this, ContactListActivity.class);
		startActivity(intent);
	}
}
