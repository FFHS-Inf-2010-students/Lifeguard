package ch.ffhs.esa.lifeguard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import ch.ffhs.esa.lifeguard.alarm.AlarmService;
import ch.ffhs.esa.lifeguard.alarm.AlarmService.AlarmBinder;
import ch.ffhs.esa.lifeguard.domain.Contact;

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

    /*//////////////////////////////////////////////////////////////////////////
     * CREATION
     */
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Intent intent = new Intent(this, AlarmService.class);
        Log.d(MainActivity.class.toString(), "Start Service");
        startService(intent);
        
        Log.d(MainActivity.class.toString(), "Before Bind");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        Log.d(MainActivity.class.toString(), "After Bind");
        
        Button sosButton = (Button) findViewById(R.id.SOSButton);

        sosButton.setOnLongClickListener(new OnLongClickListener() { 
                @Override
                public boolean onLongClick(View v) {
                    Log.d(MainActivity.class.toString(), "Long Click");
                    alarmService.doAlarm();
                    return true;
                }
            });
    }
    
    @Override
    protected void onStart() {
        super.onStart();
                
        if ( alarmService == null ) {
            Log.d(MainActivity.class.toString(), "Service not started yet.");
        } else {
            Log.d(MainActivity.class.toString(), alarmService.getState().toString());
        }
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
}
