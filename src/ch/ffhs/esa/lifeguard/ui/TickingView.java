package ch.ffhs.esa.lifeguard.ui;

import ch.ffhs.esa.lifeguard.R;
import android.app.Activity;
import android.content.Intent;
import android.widget.ProgressBar;

public class TickingView
    implements ViewStateStrategy
{
    @Override
    public void handleUi (Activity activity, Intent intent)
    {
        ProgressBar bar = (ProgressBar) activity.findViewById (R.id.progressBarDelay);
        bar.setProgress (0);
    }
}
