package ch.ffhs.esa.lifeguard;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Android activity to set application configurations.
 * 
 * @author Juerg Gutknecht <juerg.gutknecht@students.ffhs.ch>
 *
 */
public class ConfigurationActivity extends Activity {

	/*//////////////////////////////////////////////////////////////////////////
	 * CREATION
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuration);
		// Show the Up button in the action bar.
//		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
//	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
//	private void setupActionBar() {
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//			getActionBar().setDisplayHomeAsUpEnabled(true);
//		}
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.configuration, menu);
		return super.onCreateOptionsMenu (menu);
	}
	
	
	/*//////////////////////////////////////////////////////////////////////////
	 * EVENT HANDLING
	 */

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.menu.main:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
