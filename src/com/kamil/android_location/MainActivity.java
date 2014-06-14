package com.kamil.android_location;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener,
		LocationListener {

	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	
	LocationClient mLocationClient;
	
    private static final int MILLIS_PER_SECOND = 1000;
    // Update frequency in milliseconds
    private static final long UPDATE_INTERVAL = MILLIS_PER_SECOND * 10;
    // A fast frequency ceiling in milliseconds
    private static final long FASTEST_INTERVAL = MILLIS_PER_SECOND * 10;
    LocationRequest mLocationRequest;
    
    boolean mUpdatesRequested;
    private boolean mServicesConnected;
    
    private static final String LOG_TAG = "Location Services";
    
    SharedPreferences mPrefs;
    Editor mEditor;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mServicesConnected = servicesConnected();
        if (mServicesConnected) {
			mLocationClient = new LocationClient(this, this, this);
			
			mLocationRequest = LocationRequest.create();
			mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
			mLocationRequest.setInterval(UPDATE_INTERVAL);
			mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
		}
		// Open the shared preferences
		mPrefs = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
		// Get a SharedPreferences editor
		mEditor = mPrefs.edit();
		mUpdatesRequested = false;
    }
    
    /*
     * Called when the Activity becomes visible.
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (mServicesConnected) {
        	mLocationClient.connect();
        }
    }

    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    protected void onStop() {
        // when disconnected nothing will not be pulling location, unless
    	// another app is pulling, so when you reconnect it could get an old location
		if (mServicesConnected) {
			if (mLocationClient.isConnected()) {
				mLocationClient.removeLocationUpdates(this);
			}

			mLocationClient.disconnect();
		}
        super.onStop();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
        // Save the current setting for updates
        mEditor.putBoolean("KEY_UPDATES_ON", mUpdatesRequested);
        mEditor.commit();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
        /*
         * Get any previous setting for location updates
         * Gets "false" if an error occurs
         */
        if (mPrefs.contains("KEY_UPDATES_ON")) {
            mUpdatesRequested = mPrefs.getBoolean("KEY_UPDATES_ON", false);
        } else {
            mEditor.putBoolean("KEY_UPDATES_ON", false);
            mEditor.commit();
        }
    }
    
    // Define a DialogFragment that displays the error dialog
    public static class ErrorDialogFragment extends DialogFragment {
        // Global field to contain the error dialog
        private Dialog mDialog;
        // Default constructor. Sets the dialog field to null
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }
        // Set the dialog to display
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }
        // Return a Dialog to the DialogFragment.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }
    
	private boolean servicesConnected() {
		// Check that Google Play services is available
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// In debug mode, log the status
			Log.d(LOG_TAG, "Google Play services is available.");
			return true;
		} else { // Google Play services was not available for some reason
			// Get the error dialog from Google Play services
//			Dialog errorDialog = 
//					GooglePlayServicesUtil.getErrorDialog(resultCode, this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
//			// If Google Play services can provide an error dialog
//			if (errorDialog != null) {
//				// Create a new DialogFragment for the error dialog
//				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
//				// Set the dialog in the DialogFragment
//				errorFragment.setDialog(errorDialog);
//				// Show the error dialog in the DialogFragment
//				errorFragment.show(getFragmentManager(), LOCATION_SERVICE);
//			}
			Log.d(LOG_TAG, "Google Play services is NOT available.");
			return false;
		}
	}
	
	/*
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     */
    @Override
    public void onConnected(Bundle dataBundle) {
        // Display the connection status
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "Connected Location Client");
        
//        if (mUpdatesRequested) {
        	Log.d(LOG_TAG, "Requesting Location Updates");
            mLocationClient.requestLocationUpdates(mLocationRequest, this);
//        }
    }

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onDisconnected() {
        // Display the connection status
        Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "Disconnected Location Client");
    }

    /*
     * Called by Location Services if the attempt to
     * Location Services fails.
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
    	Log.d(LOG_TAG, "Connection to Location Client FAILED!");
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                * Thrown if Google Play services canceled the original
                * PendingIntent
                */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Toast.makeText(this, "Location Service connection failed: " + connectionResult.getErrorCode(), Toast.LENGTH_SHORT).show();
        }
    }

	@Override
	public void onLocationChanged(Location location) {
		Log.d(LOG_TAG, "Got Location Update");
		if (location != null) {
			String msg = "Updated Location: "
					+ Double.toString(location.getLatitude()) + ","
					+ Double.toString(location.getLongitude());
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Empty Location", Toast.LENGTH_SHORT).show();
		}
		
	}

}
