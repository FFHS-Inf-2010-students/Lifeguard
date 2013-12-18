package ch.ffhs.esa.lifeguard;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import ch.ffhs.esa.lifeguard.domain.Contact;
import ch.ffhs.esa.lifeguard.domain.ContactInterface;
import ch.ffhs.esa.lifeguard.domain.Contacts;
import ch.ffhs.esa.lifeguard.domain.ContactsInterface;
import ch.ffhs.esa.lifeguard.domain.ContactsListAdapter;

/**
 * Android activity to display a list of all available contacts.
 * 
 * @author Juerg Gutknecht <juerg.gutknecht@students.ffhs.ch>
 * @author Thomas Aregger <thomas.aregger@students.ffhs.ch>
 *
 */
public class ContactListActivity extends ListActivity {

    static final String[] ENTRIES = {
        "1. Jane Doe", "2. Hans Gseh", "3. Max Muster"
    };
    
    /*//////////////////////////////////////////////////////////////////////////
     * PROPERTIES
     */
    
    private ContactsInterface dataSource;
    
    /*//////////////////////////////////////////////////////////////////////////
     * CREATION
     */
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show the Up button in the action bar.
//        setupActionBar();
        
        this.dataSource = new Contacts(Lifeguard.getDatabaseHelper());
        
        List<ContactInterface> contacts = this.dataSource.getAll();
        this.setListAdapter(new ContactsListAdapter(this, contacts));
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact_list, menu);
        return true;
    }

    
    /*//////////////////////////////////////////////////////////////////////////
     * EVENT HANDLING
     */
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        // TODO R.id.home doesn't exist
//        case R.id.home:
//            finish ();
//            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        ContactInterface contact = (Contact)l.getItemAtPosition(position);

        Intent intent = new Intent(this, ContactDetailActivity.class);
        intent.putExtra("contact", contact);
        
        startActivity(intent);
    }
}
