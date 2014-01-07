package ch.ffhs.esa.lifeguard.ui;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * A special listener for the SOS button. Use a separate thread to measure a
 * x-seconds click instead of using the standard OnLongClickInterface (which is
 * as the name says only for long clicks (3 seconds or so, not configurable).
 * Using OnTouchListener allows us to define own delays.
 * 
 * @author Christof Kälin <christof.kaelin@students.ffhs.ch>
 * @author Juerg Gutknecht <juerg.gutknecht@students.ffhs.ch>
 * 
 */

public class SOSButtonListener implements OnTouchListener {
    // private static final Integer DEFAULT_DELAY_MSECS = 5000;
    // private AlarmContext alarmContext;
    private final long buttonDelay;
    private final Handler handler = new Handler();
    // private final boolean pressed = false;
    /** The action to run */
    private final Runnable realHandler;

    /**
     * Creates a new {@link SOSButtonListener}.
     * 
     * @param handler
     *            the handler to run on button activation
     */
    public SOSButtonListener(Runnable handler, long buttonDelay) {
        realHandler = handler;
        this.buttonDelay = buttonDelay;

    }

    // This does NOT work as intended! Long clicks are not configurable
    // @Override
    // public boolean onLongClick(View v) {
    // pressed = (pressed) ? false : true;
    // if (pressed)
    // handler.postDelayed(realHandler, DELAY_MSECS);
    // else
    // handler.removeCallbacks(realHandler);
    // return true;
    // }

    /*
     * Implements OnTouchListener. Puts a delay message in the queue of the
     * thread (through it's designated Handler)
     * 
     * @see android.view.View.OnTouchListener#onTouch(android.view.View,
     * android.view.MotionEvent)
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO: Visual button feedback (while pressing button) should be
        // possible
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            handler.postDelayed(realHandler, buttonDelay);
            break;
        case MotionEvent.ACTION_UP:
            handler.removeCallbacks(realHandler);
            break;
        }

        return true;
    }

    /**
     * Creates a new {@link SOSButtonListener} with the given handler.
     * 
     * @param handler
     *            the handler to run on button activation
     * @return
     */
    public static SOSButtonListener create(Runnable handler, long buttonDelay) {
        return new SOSButtonListener(handler, buttonDelay);
    }

}