/**
 * Copyright 2010-present Facebook.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.champbars;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.champbars.*;
import com.facebook.*;
import com.facebook.android.Facebook;
import com.facebook.android.Util;
import com.facebook.model.GraphMultiResult;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphPlace;
import com.facebook.model.GraphUser;
import com.facebook.widget.*;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Main class of our app - other activities spawn from here
 * @author tmadigan7
 *
 */
public class MainActivity extends FragmentActivity {

    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
    private static final Location CHAMP_LOCATION = new Location("") {
        {
            setLatitude(40.110231);
            setLongitude(-88.238679);
        }
    };

    private final String PENDING_ACTION_BUNDLE_KEY = "com.example.champbars:PendingAction";
    
 /* ---------- MAIN SCREEN BUTTONS ----------- */
    
    private Button barsButton;
    private Button checkinButton;
    private Button commitButton;
    private Button viewFriendsButton;
    private Button pickPlaceButton;
    private LoginButton loginButton;
    private List<GraphUser> friendsWithApp;
    //private Button postStatusUpdateButton;
    //private Button postPhotoButton;
    
/* ---------- necessary private vars ----------- */
    
    private ProfilePictureView profilePictureView;
    //private TextView greeting;
    private PendingAction pendingAction = PendingAction.NONE;
    private ViewGroup controlsContainer;
    private GraphUser user;

/* ---------- CODE BELOW ----------- */
    
    private enum PendingAction {
        NONE,
        POST_PHOTO,
        POST_STATUS_UPDATE
    }
    private UiLifecycleHelper uiHelper;

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    /**
     * Method called when this activity is launched
     */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            String name = savedInstanceState.getString(PENDING_ACTION_BUNDLE_KEY);
            pendingAction = PendingAction.valueOf(name);
        }

        setContentView(R.layout.activity_main);

/* ------------------- INIT AND SET UP BUTTON CALLBACKS -------------------  */
        
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                MainActivity.this.user = user;
                updateUI();
                // It's possible that we were waiting for this.user to be populated in order to post a
                // status update.
                handlePendingAction();
            }
        });

        // set up profile pic and callback
        profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
        //greeting = (TextView) findViewById(R.id.greeting);

        barsButton = (Button) findViewById(R.id.barsButton);
        barsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent intent = new Intent(MainActivity.this, MapActivity.class);
        		startActivity(intent);
            }
        });
        
        // set up checkin button and callback
        checkinButton = (Button) findViewById(R.id.checkinButton);
        checkinButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent intent = new Intent(MainActivity.this, CheckInActivity.class);
        		startActivity(intent);
            }
        });
        
        // set up bar commit button and callback
        commitButton = (Button) findViewById(R.id.commitButton);
        commitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent intent = new Intent(MainActivity.this, CommitActivity.class);
        		startActivity(intent);
            }
        });
        
        // set up friend search button and callback
        viewFriendsButton = (Button) findViewById(R.id.viewFriendsButton);
        viewFriendsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickPickFriends();
            }
        });

        // set up location selector button and callback
        pickPlaceButton = (Button) findViewById(R.id.pickPlaceButton);
        pickPlaceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickPickPlace();
            }
        });
        
/* ---------- BUTTONS UNUSED AT THIS POINT-----------
 
        postStatusUpdateButton = (Button) findViewById(R.id.postStatusUpdateButton);
        postStatusUpdateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickPostStatusUpdate();
            }
        });

        //        postPhotoButton = (Button) findViewById(R.id.postPhotoButton);
        postPhotoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickPostPhoto();
            }
        });
*/

/* ------------- SET UP CONTAINER AND OTHER HELPER FUNCTIONS --------------- */
        
        controlsContainer = (ViewGroup) findViewById(R.id.main_ui_container);

        final FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment != null) {
            // If we're being re-created and have a fragment, we need to a) hide the main UI controls and
            // b) hook up its listeners again.
            controlsContainer.setVisibility(View.GONE);
            if (fragment instanceof FriendPickerFragment) {
                setFriendPickerListeners((FriendPickerFragment) fragment);
            } else if (fragment instanceof PlacePickerFragment) {
                setPlacePickerListeners((PlacePickerFragment) fragment);
            }
        }

        // Listen for changes in the back stack so we know if a fragment got popped off because the user
        // clicked the back button.
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (fm.getBackStackEntryCount() == 0) {
                    // We need to re-show our UI.
                    controlsContainer.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();

        updateUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);

        outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    /**
     * Function called when the state is changed, need to update everything
     * @param session Facebook session currently running
     * @param state state of app
     * @param exception any possible exception recieved
     */
    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (pendingAction != PendingAction.NONE &&
                (exception instanceof FacebookOperationCanceledException ||
                exception instanceof FacebookAuthorizationException)) {
                new AlertDialog.Builder(MainActivity.this)
                    .setTitle(R.string.cancelled)
                    .setMessage(R.string.permission_not_granted)
                    .setPositiveButton(R.string.ok, null)
                    .show();
            pendingAction = PendingAction.NONE;
        } else if (state == SessionState.OPENED_TOKEN_UPDATED) {
            handlePendingAction();
        }
        updateUI();
    }

    /**
     * Function to update the screen and enable buttons upon a successful login
     */
    private void updateUI() {
        Session session = Session.getActiveSession();
        boolean enableButtons = (session != null && session.isOpened());

//      postStatusUpdateButton.setEnabled(enableButtons);
//      postPhotoButton.setEnabled(enableButtons);
        barsButton.setEnabled(enableButtons);
        checkinButton.setEnabled(enableButtons);
        commitButton.setEnabled(enableButtons);
        viewFriendsButton.setEnabled(enableButtons);
        pickPlaceButton.setEnabled(enableButtons);

        if (enableButtons && user != null) {
            profilePictureView.setProfileId(user.getId());
            //greeting.setText(getString(R.string.champBars, user.getFirstName()));
        } else {
            profilePictureView.setProfileId(null);
            //greeting.setText(null);
        }
    }

    @SuppressWarnings("incomplete-switch")
    private void handlePendingAction() {
        PendingAction previouslyPendingAction = pendingAction;
        // These actions may re-set pendingAction if they are still pending, but we assume they
        // will succeed.
        pendingAction = PendingAction.NONE;

        switch (previouslyPendingAction) {
            case POST_PHOTO:
                postPhoto();
                break;
            case POST_STATUS_UPDATE:
                postStatusUpdate();
                break;
        }
    }

    private interface GraphObjectWithId extends GraphObject {
        String getId();
    }

    /**
     * Function to publish "___ selected" message
     * @param message msg to be displayed
     * @param result list of friends or places selected
     * @param error possible error code
     */
    private void showPublishResult(String message, GraphObject result, FacebookRequestError error) {
        String title = null;
        String alertMessage = null;
        if (error == null) {
            title = getString(R.string.success);
            String id = result.cast(GraphObjectWithId.class).getId();
            alertMessage = getString(R.string.successfully_posted_post, message, id);
        } else {
            title = getString(R.string.error);
            alertMessage = error.getErrorMessage();
        }

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(alertMessage)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

//    private void onClickPostStatusUpdate() {
//        performPublish(PendingAction.POST_STATUS_UPDATE);
//    }

    private void postStatusUpdate() {
        if (user != null && hasPublishPermission()) {
            final String message = getString(R.string.status_update, user.getFirstName(), (new Date().toString()));
            Request request = Request
                    .newStatusUpdateRequest(Session.getActiveSession(), message, new Request.Callback() {
                        @Override
                        public void onCompleted(Response response) {
                            showPublishResult(message, response.getGraphObject(), response.getError());
                        }
                    });
            request.executeAsync();
        } else {
            pendingAction = PendingAction.POST_STATUS_UPDATE;
        }
    }

    private void onClickPostPhoto() {
        performPublish(PendingAction.POST_PHOTO);
    }

    private void postPhoto() {
        if (hasPublishPermission()) {
            Bitmap image = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);
            Request request = Request.newUploadPhotoRequest(Session.getActiveSession(), image, new Request.Callback() {
                @Override
                public void onCompleted(Response response) {
                    showPublishResult(getString(R.string.photo_post), response.getGraphObject(), response.getError());
                }
            });
            request.executeAsync();
        } else {
            pendingAction = PendingAction.POST_PHOTO;
        }
    }

    private void showPickerFragment(PickerFragment<?> fragment) {
        fragment.setOnErrorListener(new PickerFragment.OnErrorListener() {
            @Override
            public void onError(PickerFragment<?> pickerFragment, FacebookException error) {
                String text = getString(R.string.exception, error.getMessage());
                Toast toast = Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

        controlsContainer.setVisibility(View.GONE);

        // We want the fragment fully created so we can use it immediately.
        fm.executePendingTransactions();

        fragment.loadData(false);
    }

    /**
     * Function to select friends from View Friends button
     */
    private void onClickPickFriends() {
        //final FriendPickerFragment fragment = new FriendPickerFragment();

        // DEBUG - FIND FRIENDS
        Session session = Session.getActiveSession();
        if(session != null && session.isOpened())
        {
        	System.out.println("OK SESSION, IN ONCLICK CALLBACK");

        	// genereate request to get my friends
        	Request myFriends = requestFriends(session);
        	myFriends.setCallback(new Request.Callback() {

                @Override
                public void onCompleted(Response response) {
                    List<GraphUser> friends = getResults(response);

                    ArrayList<String> names = new ArrayList<String>();
                    String friendsWithApp;
                    
                    for(int i = 0; i < friends.size(); i++)
                    {
                    	if(friends.get(i).getProperty("installed") != null)
                    	{
                    		names.add(user.getName());
                    	}
                    }
                    friendsWithApp = TextUtils.join(", ", names);
                    showAlert("People with this app:", friendsWithApp);
                }
            });
            myFriends.executeAsync();
        }
        
//        setFriendPickerListeners(fragment);
//
//        showPickerFragment(fragment);
    }
    
    /**
     * Get the results of my request to get user's friends
     * @param response
     * @return
     */
    private List<GraphUser> getResults(Response response) 
    {
        GraphMultiResult multiResult = response.getGraphObjectAs(GraphMultiResult.class);
        
        GraphObjectList<GraphObject> data = multiResult.getData();
        return data.castToListOf(GraphUser.class);
    }

    private Request requestFriends(Session session) 
    {
    	Request request = Request.newGraphPathRequest(session, "me/friends", null);

        Set<String> fields = new HashSet<String>();
        String[] requiredFields = new String[] { "id", "name", "picture", "installed" };
        fields.addAll(Arrays.asList(requiredFields));

        Bundle parameters = request.getParameters();
        parameters.putString("fields", TextUtils.join(",", fields));
        request.setParameters(parameters);

        return request;
	}

	private void setFriendPickerListeners(final FriendPickerFragment fragment) {
        fragment.setOnDoneButtonClickedListener(new FriendPickerFragment.OnDoneButtonClickedListener() {
            @Override
            public void onDoneButtonClicked(PickerFragment<?> pickerFragment) {
                onFriendPickerDone(fragment);
            }
        });
    }

    /**
     * Function to set up result list of selected friends once done button is clicked
     * @param fragment
     */
    private void onFriendPickerDone(FriendPickerFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();

        String results = "";

        Collection<GraphUser> selection = fragment.getSelection();
        if (selection != null && selection.size() > 0) {
            ArrayList<String> names = new ArrayList<String>();
            for (GraphUser user : selection) {
                names.add(user.getName());
            }
            results = TextUtils.join(", ", names);
        } else {
            results = getString(R.string.no_friends_selected);
        }

        showAlert(getString(R.string.you_picked), results);
    }

    /**
     * Function to set up result list of selected places once done button is clicked
     * @param fragment
     */
    private void onPlacePickerDone(PlacePickerFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();

        String result = "";

        GraphPlace selection = fragment.getSelection();
        if (selection != null) {
            result = selection.getName();
        } else {
            result = getString(R.string.no_place_selected);
        }

        showAlert(getString(R.string.you_picked), result);
    }

    /**
     * Function to select places from Pick a Place button
     */
    private void onClickPickPlace() {
        final PlacePickerFragment fragment = new PlacePickerFragment();
        fragment.setLocation(CHAMP_LOCATION);
        fragment.setTitleText(getString(R.string.pick_champ_place));

        setPlacePickerListeners(fragment);

        showPickerFragment(fragment);
    }

    private void setPlacePickerListeners(final PlacePickerFragment fragment) {
        fragment.setOnDoneButtonClickedListener(new PlacePickerFragment.OnDoneButtonClickedListener() {
            @Override
            public void onDoneButtonClicked(PickerFragment<?> pickerFragment) {
                onPlacePickerDone(fragment);
            }
        });
        fragment.setOnSelectionChangedListener(new PlacePickerFragment.OnSelectionChangedListener() {
            @Override
            public void onSelectionChanged(PickerFragment<?> pickerFragment) {
                if (fragment.getSelection() != null) {
                    onPlacePickerDone(fragment);
                }
            }
        });
    }

    /**
     * Function to display the result from friend/place selection
     * @param title response (friend/place)
     * @param message result msg
     */
    public void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }
 
    private boolean hasPublishPermission() {
        Session session = Session.getActiveSession();
        return session != null && session.getPermissions().contains("publish_actions");
    }

    private void performPublish(PendingAction action) {
        Session session = Session.getActiveSession();
        if (session != null) {
            pendingAction = action;
            if (hasPublishPermission()) {
                // We can do the action right away.
                handlePendingAction();
            } else {
                // We need to get new permissions, then complete the action when we get called back.
                session.requestNewPublishPermissions(new Session.NewPermissionsRequest(this, PERMISSIONS));
            }
        }
    }
}
