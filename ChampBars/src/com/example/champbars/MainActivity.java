package com.example.champbars;

import android.os.Bundle;
import com.google.android.gcm.GCMRegistrar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import com.facebook.*;
import com.facebook.model.*;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// start Facebook Login
		Session.openActiveSession(this, true, new Session.StatusCallback() {

			// callback when session changes state
			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				if (session.isOpened()) {

					// make request to the /me API
					Request.executeMeRequestAsync(session,
							new Request.GraphUserCallback() {

								// callback after Graph API response with user
								// object
								@Override
								public void onCompleted(GraphUser user,
										Response response) {
									if (user != null) {
										//Move the user to the 3 button main screen after login is accomplished
										Intent mainscreen = new Intent(MainActivity.this, MainScreen.class);
										startActivity(mainscreen);
									}
								}
							});
				}
			}
		});
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
	}

}