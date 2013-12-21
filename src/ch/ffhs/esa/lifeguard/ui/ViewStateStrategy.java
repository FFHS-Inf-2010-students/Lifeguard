package ch.ffhs.esa.lifeguard.ui;

import android.app.Activity;
import android.content.Intent;

/**
 * Sets the current view controls to their desired state.
 * 
 * @author David Daniel <david.daniel@students.ffhs.ch>
 */
public interface ViewStateStrategy
{
    /**
     * Sets all appropriate ui controls to their correct state.
     * @param activity the activity holding the ui controls
     * @param intent the original intent (the source event, the message)
     */
    public void handleUi (Activity activity, Intent intent);

    /**
     * Called when the ui gets closed.
     */
    public void onClose ();
}
