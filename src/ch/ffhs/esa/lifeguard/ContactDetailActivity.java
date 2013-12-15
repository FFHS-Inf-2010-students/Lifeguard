package ch.ffhs.esa.lifeguard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import ch.ffhs.esa.lifeguard.domain.Contact;
import ch.ffhs.esa.lifeguard.domain.ContactInterface;
import ch.ffhs.esa.lifeguard.domain.Contacts;
import ch.ffhs.esa.lifeguard.domain.ContactsInterface;

/**
 * Android activity to display a given contact's detailed information.
 * 
 * @author Juerg Gutknecht <juerg.gutknecht@students.ffhs.ch>
 *
 */
public class ContactDetailActivity extends Activity {

	/*//////////////////////////////////////////////////////////////////////////
	 * PROPERTIES
	 */
	
	private ContactInterface contact;
	
	
	/*//////////////////////////////////////////////////////////////////////////
	 * CREATION
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_detail);

//		setupActionBar();
		Intent intent = this.getIntent();
		
		int count = 1;
		if (intent.hasExtra("contact")) {
			this.contact = (Contact)intent.getSerializableExtra("contact");
			count = intent.getIntExtra("count", 1);
		} else {
			this.contact = new Contact();
			count = intent.getIntExtra("count", 1);
			count++;
		}
		
		String positions[] = new String[count];
        for (int i = 0; i < count; ++i) {
            positions[i] = String.valueOf(i+1);
        }
        Spinner spinner = this.getPositionSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, positions
        );
        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        );
        spinner.setAdapter(adapter);
		
		this.populate();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
//	private void setupActionBar() {
//		getActionBar().setDisplayHomeAsUpEnabled(true);
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_detail, menu);
		return true;
	}

	
	/*//////////////////////////////////////////////////////////////////////////
	 * EVENT HANDLING
	 */
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// TODO R.id.home doesn't exist
//		case R.id.home:
//			NavUtils.navigateUpFromSameTask(this);
//			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void saveContact(View view) {
		Log.d(ContactDetailActivity.class.getName(), "Saving contact...");
		contact.setName(this.getNameField().getText().toString());
		contact.setPhone(this.getPhoneField().getText().toString());
		contact.setPosition(Integer.parseInt((String) this.getPositionSpinner().getSelectedItem()));
		ContactsInterface contacts = new Contacts(Lifeguard.getDatabaseHelper());
		if (contacts.persist(contact) > 0L) {
		    finish ();
		}
	}
	
	
	/*//////////////////////////////////////////////////////////////////////////
	 * VIEW OPERATIONS
	 */
	
	protected void populate() {
		this.getNameField().setText(this.contact.getName());
		this.getPhoneField().setText(this.contact.getPhone());
		
		Spinner s = this.getPositionSpinner();
		if (this.contact.getPosition() > 0) {
		    s.setSelection(this.contact.getPosition()-1);
		} else {
		    s.setSelection(s.getCount()-1); // new contact => set highest position
		}
	}
	
	protected EditText getNameField() {
		return (EditText)this.findViewById(R.id.contactDetailName);
	}
	
	protected EditText getPhoneField() {
		return (EditText)this.findViewById(R.id.contactDetailPhone);
	}
	
	protected Spinner getPositionSpinner() {
	    return (Spinner)this.findViewById(R.id.contactDetailPosition);
	}
}
