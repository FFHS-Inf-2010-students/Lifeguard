package ch.ffhs.esa.lifeguard.alarm.state;

import android.content.Intent;
import ch.ffhs.esa.lifeguard.domain.ContactInterface;

/**
 * Handles the confirmation of the rescuers reply.
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 */
public class ConfirmedState
    extends AbstractAlarmState
{
    /** The contact that confirmed the alarm message */
    private ContactInterface contact;

    /*//////////////////////////////////////////////////////////////////////////
     * PUBLIC INTERFACE
     */

    /**
     * Constructs a confirmed state with the given contact as the source of the
     * confirmation.
     * @param contact the contact that confirmed the alarm message
     */
    public ConfirmedState (ContactInterface contact)
    {
        this.contact = contact;
    }


    @Override
    public AlarmStateId getId ()
    {
        return AlarmStateId.CONFIRMED;
    }

    /*//////////////////////////////////////////////////////////////////////////
     * PROTECTED OPERATIONS
     */

    @Override
    protected void start ()
    {
        // nothing to do
    }

    @Override
    public void getStateInfo (Intent intent)
    {
        intent.putExtra ("contactId", contact.getId ());
    }
}
