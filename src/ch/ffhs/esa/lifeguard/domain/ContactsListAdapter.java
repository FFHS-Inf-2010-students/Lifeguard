package ch.ffhs.esa.lifeguard.domain;

import java.util.List;

import android.content.Context;
import ch.ffhs.esa.lifeguard.persistence.TableGatewayTwoLineAdapter;

/**
 * List adapter for contacts.
 * 
 * @author Juerg Gutknecht <juerg.gutknecht@students.ffhs.ch>
 *
 */
public class ContactsListAdapter
	extends TableGatewayTwoLineAdapter<ContactInterface> {

	/*//////////////////////////////////////////////////////////////////////////
	 * INITIALIZATION
	 */
	
	public ContactsListAdapter(Context context, List<ContactInterface> contacts) {
		super(context, contacts);
	}

	
	/*//////////////////////////////////////////////////////////////////////////
	 * PUBLIC INTERFACE
	 */
	
	@Override
	public String getLineOneText(ContactInterface object) {
		return object.getName();
	}

	@Override
	public String getLineTwoText(ContactInterface object) {
		return object.getPhone();
	}
}
