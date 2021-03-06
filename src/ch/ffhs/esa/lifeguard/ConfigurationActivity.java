package ch.ffhs.esa.lifeguard;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * Android activity to set application configurations.
 * 
 * @author Juerg Gutknecht <juerg.gutknecht@students.ffhs.ch>
 * 
 */
public class ConfigurationActivity extends Activity {

    /** The application */
    private Lifeguard application;

    /*
     * //////////////////////////////////////////////////////////////////////////
     * CREATION
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (Lifeguard) getApplication();
        setContentView(R.layout.activity_configuration);
        loadSettings();
        // Show the Up button in the action bar.
        // setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    // @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    // private void setupActionBar() {
    // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
    // getActionBar().setDisplayHomeAsUpEnabled(true);
    // }
    // }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.configuration, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*
     * //////////////////////////////////////////////////////////////////////////
     * EVENT HANDLING
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.menu.main:
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveConfiguration(View view) {
        SharedPreferences prefs = getSharedPreferences(
                application.getSharedPreferencesIdentifier(), MODE_PRIVATE);
        Editor editor = prefs.edit();

        EditText item = (EditText) findViewById(R.id.configurationUserName);
        editor.putString(getString(R.string.userNameConfigurationKey), item
                .getText().toString());

        item = (EditText) findViewById(R.id.configurationDelay);
        editor.putString(getString(R.string.alarmDelayConfigurationKey), item
                .getText().toString());

        item = (EditText) findViewById(R.id.configurationRepeatDelay);
        editor.putString(getString(R.string.alarmRepeatDelayConfigurationKey),
                item.getText().toString());

        item = (EditText) findViewById(R.id.configurationRepeatDelay);
        editor.putString(getString(R.string.alarmButtonDelayConfigurationKey),
                item.getText().toString());

        editor.commit();

        finish();
    }

    public void cancelConfiguration(View view) {
        finish();
    }

    private void loadSettings() {
        SharedPreferences prefs = getSharedPreferences(
                application.getSharedPreferencesIdentifier(), MODE_PRIVATE);

        EditText item = (EditText) findViewById(R.id.configurationUserName);
        item.setText(prefs.getString(
                getString(R.string.userNameConfigurationKey), ""));

        item = (EditText) findViewById(R.id.configurationDelay);
        item.setText(prefs.getString(
                getString(R.string.alarmDelayConfigurationKey), ""));

        item = (EditText) findViewById(R.id.configurationRepeatDelay);
        item.setText(prefs.getString(
                getString(R.string.alarmRepeatDelayConfigurationKey), ""));

        item = (EditText) findViewById(R.id.configurationRepeatDelay);
        item.setText(prefs.getString(
                getString(R.string.alarmButtonDelayConfigurationKey), ""));
    }
}
