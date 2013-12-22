package ch.ffhs.esa.lifeguard;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
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
	private Intent uiMessageIntent;

	private BroadcastReceiver stateChangeReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			onStateChanged(intent);
		}
	};

	private BroadcastReceiver uiMessgageReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			setUiMessageIntent(intent);
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

		registerReceiver(uiMessgageReceiver, new IntentFilter(
				ServiceMessage.UI_MESSAGE));

		Intent intent = new Intent(this, AlarmService.class);
		Log.d(MainActivity.class.toString(), "Start Service");
		startService(intent);

		Log.d(MainActivity.class.toString(), "Before Bind");
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
		Log.d(MainActivity.class.toString(), "After Bind");

		Button sosButton = (Button) findViewById(R.id.SOSButton);

		final Handler handler = new Handler();
		final Runnable mLongPressed = new Runnable() {
			public void run() {
				// Toast.makeText(MainActivity.this, "Long press!",
				// Toast.LENGTH_LONG).show();
				Log.i(this.getClass().toString(), "Long press!");
				AlarmStateId state = alarmService.getContext().getState()
						.getId();
				Log.d(MainActivity.class.toString(), "Clicked button");

				if (state == AlarmStateId.AWAITING) {
					cancelManualAlarm();
				} else {
					triggerManualAlarm();
				}
			}
		};

		// TODO: Visual button feedback (pressing button) should be possible
		sosButton.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					handler.postDelayed(mLongPressed, 5000);
				if ((event.getAction() == MotionEvent.ACTION_MOVE)
						|| (event.getAction() == MotionEvent.ACTION_UP))
					handler.removeCallbacks(mLongPressed);
				return true;
			}
		});

		// sosButton.setOnLongClickListener(new OnLongClickListener() {
		// @Override
		// public boolean onLongClick(View v) {
		// AlarmStateId state = alarmService.getContext().getState().getId();
		// Log.d(MainActivity.class.toString(), "Clicked button");
		//
		// if (state == AlarmStateId.AWAITING) {
		// cancelManualAlarm();
		// } else {
		// triggerManualAlarm ();
		// }
		// return true;
		// }
		// });
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
		unregisterReceiver(uiMessgageReceiver);
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

	private void triggerManualAlarm() {
		Intent intent = new Intent(ActivityMessage.STATE_CHANGE_REQUEST);
		intent.putExtra(ActivityMessage.Key.ALARM_STATE_ID,
				AlarmStateId.ALARMING.toString());

		sendBroadcast(intent);
	}

	private void cancelManualAlarm() {
		Intent intent = new Intent(ActivityMessage.STATE_CHANGE_REQUEST);
		intent.putExtra(ActivityMessage.Key.ALARM_STATE_ID,
				AlarmStateId.INIT.toString());

		sendBroadcast(intent);
	}

	private void setUiMessageIntent(Intent intent) {
		uiMessageIntent = intent;
	}

	private void putUiMessage(Intent intent) {
		if (uiMessageIntent != null) {
			Bundle extras = uiMessageIntent.getExtras();
			if (extras != null) {
				intent.putExtras(extras);
			}
		}
	}

	private void onStateChanged(Intent intent) {
		Bundle bundle = intent.getExtras();
		putUiMessage(intent);

		AlarmStateId current = AlarmStateId.valueOf(AlarmStateId.class, bundle
				.get(ServiceMessage.Key.ALARM_STATE_ID).toString());

		Log.d(getClass().toString(), "State Change: " + current.toString());

		viewStrategyFactory.create(current).handleUi(this, intent);
	}
}
