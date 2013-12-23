package ch.ffhs.esa.lifeguard;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import ch.ffhs.esa.lifeguard.alarm.AlarmService;
import ch.ffhs.esa.lifeguard.alarm.AlarmService.AlarmBinder;
import ch.ffhs.esa.lifeguard.alarm.ServiceMessage;
import ch.ffhs.esa.lifeguard.alarm.state.AlarmStateId;
import ch.ffhs.esa.lifeguard.ui.ViewStrategyFactory;

/**
 * The application's main activity (aka home screen).
 * 
 * @author Thomas Aregger <thomas.aregger@students.ffhs.ch>
 * @author Juerg Gutknecht <juerg.gutknecht@students.ffhs.ch>
 * 
 */
public class MainActivity extends Activity {
	AlarmService alarmService;
	boolean bound = false;

	private BroadcastReceiver stateChangeReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			onStateChanged(intent);
		}
	};

	private ViewStrategyFactory viewStrategyFactory = new ViewStrategyFactory();

	/*
	 * //////////////////////////////////////////////////////////////////////////
	 * CREATION
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		registerReceiver(stateChangeReceiver, new IntentFilter(
				ServiceMessage.CURRENT_SERVICE_STATE));

		Intent intent = new Intent(this, AlarmService.class);
		Log.d(MainActivity.class.toString(), "Start Service");
		startService(intent);

		Log.d(MainActivity.class.toString(), "Before Bind");
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
		Log.d(MainActivity.class.toString(), "After Bind");
	}

	@Override
	protected void onStart() {
		super.onStart();

		if (alarmService == null) {
			Log.d(MainActivity.class.toString(),
					"AlarmService not started yet.");
		} else {
			// Log.d(MainActivity.class.toString(),
			// alarmService.getState().toString());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/*
	 * //////////////////////////////////////////////////////////////////////////
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

	@Override
	public void onDestroy() {
		unregisterReceiver(stateChangeReceiver);
		unbindService(serviceConnection);
		viewStrategyFactory.notifiyClose();
		super.onDestroy();
	}

	/*
	 * //////////////////////////////////////////////////////////////////////////
	 * ACTIONS
	 */

	/**
	 * Starts the configuration activity.
	 */
	public void openConfiguration() {
		Intent intent = new Intent(this, ConfigurationActivity.class);
		startActivity(intent);
	}

	/**
	 * Starts the contact list activity to display all available contacts.
	 */
	public void viewContacts() {
		Intent intent = new Intent(this, ContactListActivity.class);
		startActivity(intent);
	}

	private ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			AlarmBinder binder = (AlarmBinder) service;
			alarmService = binder.getService();
			bound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			bound = false;
		}
	};

	private void onStateChanged(Intent intent) {
		Bundle bundle = intent.getExtras();

		AlarmStateId current = AlarmStateId.valueOf(AlarmStateId.class,
		        bundle.getString (ServiceMessage.Key.ALARM_STATE_ID));

		Log.d(getClass().toString(), "State Change: " + current.toString());

		viewStrategyFactory.create(current).handleUi(this, intent);
	}
}
