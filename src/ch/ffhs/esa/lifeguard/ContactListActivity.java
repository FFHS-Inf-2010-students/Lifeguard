package ch.ffhs.esa.lifeguard;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
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
        
        this.loadContacts();
        
        this.setOnItemLongClickListener();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        this.loadContacts();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    private void setupActionBar() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            getActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//    }

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
//            NavUtils.navigateUpFromSameTask(this);
//            return true;
        case R.id.addContact:
            Log.d(ContactListActivity.class.getName(), "About to show contact details (addContact)...");
            Intent intent = new Intent(this, ContactDetailActivity.class);
            this.startActivity(intent);
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        ContactInterface contact = this.getContactFromListView(l, position);

        Intent intent = new Intent(this, ContactDetailActivity.class);
        intent.putExtra("contact", contact);
        
        startActivity(intent);
    }
    
    protected void loadContacts() {
        List<ContactInterface> contacts = this.dataSource.getAll();
        this.setListAdapter(new ContactsListAdapter(this, contacts));
    }
    
    protected void setOnItemLongClickListener() {
        this.getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Log.d(ContactListActivity.class.getName(), "Long click...");
                ContactInterface contact = getContactFromListView(parent, position);
                
                Dialog dialog = this.createDeleteDialog(contact);
                
                dialog.show();
                
                return false;
            }
            
            protected Dialog createDeleteDialog(final ContactInterface contact) {
                String msg = String.format(getString(
                    R.string.contact_list_confirm_delete),
                    contact.getName()
                );
                
                AlertDialog.Builder builder = new AlertDialog.Builder(ContactListActivity.this);
                
                builder.setMessage(msg)
                   .setCancelable(false)
                   .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ContactsInterface contacts = new Contacts(Lifeguard.getDatabaseHelper());
                            
                            if (contacts.delete(contact) > 0) {
                                ContactListActivity.this.loadContacts();
                            }
                        }
                    })
                   .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                   });
                
                return builder.create();
            }
        });
    }
    
    protected ContactInterface getContactFromListView(AdapterView<?> v, int position) {
        return (Contact)v.getItemAtPosition(position);
    }
}
