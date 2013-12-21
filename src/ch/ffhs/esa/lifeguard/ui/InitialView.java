package ch.ffhs.esa.lifeguard.ui;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.ProgressBar;
import ch.ffhs.esa.lifeguard.R;

public class InitialView implements ViewStateStrategy {
	@Override
	public void handleUi(Activity activity, Intent intent) {
		Button button = (Button) activity.findViewById(R.id.SOSButton);
		button.setEnabled(false);
		button = (Button) activity.findViewById(R.id.toggleButtonAlarmSwitch);
		button.setEnabled(false);
		ProgressBar bar = (ProgressBar) activity
				.findViewById(R.id.progressBarDelay);
		bar.setProgress(0);
	}
}
