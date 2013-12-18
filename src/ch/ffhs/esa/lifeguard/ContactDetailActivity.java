package ch.ffhs.esa.lifeguard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
		Intent intent = getIntent();
		
		int count = 1;
		if (intent.hasExtra("contact")) {
			contact = (Contact)intent.getSerializableExtra("contact");
			count = intent.getIntExtra("count", 1);
		} else {
			contact = new Contact();
			count = intent.getIntExtra("count", 1);
			count++;
		}
		
		String positions[] = new String[count];
        for (int i = 0; i < count; ++i) {
            positions[i] = String.valueOf(i+1);
        }
        Spinner spinner = getPositionSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, positions
        );
        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        );
        spinner.setAdapter(adapter);
        
		populate();
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
		}
		return super.onOptionsItemSelected(item);
	}

	public void saveContact(View view) {
		if (!validate()) {
		    return;
		}
	    
	    contact.setName(getNameField().getText().toString());
		contact.setPhone(getPhoneField().getText().toString());
		contact.setPosition(
	        Integer.parseInt((String) getPositionSpinner().getSelectedItem())
        );
		ContactsInterface contacts = new Contacts(Lifeguard.getDatabaseHelper());
		if (contacts.persist(contact) > 0L) {
		    finish ();
		}
	}
	
	public void cancel(View view) {
	    finish();
	}
	
	
	/*//////////////////////////////////////////////////////////////////////////
	 * VIEW OPERATIONS
	 */
	
	protected void populate() {
		getNameField().setText(contact.getName());
		getPhoneField().setText(contact.getPhone());
		
		Spinner s = getPositionSpinner();
		if (contact.getPosition() > 0) {
		    s.setSelection(contact.getPosition()-1);
		} else {
		    s.setSelection(s.getCount()-1); // new contact => set highest position
		}
	}
	
	protected boolean validate() {
	    boolean valid = true;
	    
	    if (0 == getNameField().getText().length()) {
	        getNameField().setError(
                getString(R.string.contact_detail_error_name)
            );
	        valid = false;
	    }
	    
	    if (0 == getPhoneField().getText().length()) {
	        getPhoneField().setError(
                getString(R.string.contact_detail_error_phone)
            );
	        valid = false;
	    }
	    
	    return valid;
	}
	
	protected EditText getNameField() {
		return (EditText)findViewById(R.id.contactDetailName);
	}
	
	protected EditText getPhoneField() {
		return (EditText)findViewById(R.id.contactDetailPhone);
	}
	
	protected Spinner getPositionSpinner() {
	    return (Spinner)findViewById(R.id.contactDetailPosition);
	}
}
