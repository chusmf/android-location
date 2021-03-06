package com.kamil.android_location.geofence;

import android.app.PendingIntent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationStatusCodes;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.kamil.android_location.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class GeofenceManager implements IGeofenceManager {

    List<BasicGeofence> basicGeofences;

    public static final GeofenceManager INSTANCE = new GeofenceManager();

    public static GeofenceManager getInstance() {
        return INSTANCE;
    }

    private String geofenceTag = "";

    public String getGeofenceTag() {
        return geofenceTag;
    }

    public void setGeofenceTag(String newTag) {
        geofenceTag = newTag;
    }

    private GeofenceManager() {
        fillList();
    }

    private void fillList() {
        basicGeofences = new ArrayList<BasicGeofence>();

        basicGeofences.add(new BasicGeofence("1", "Kamil Apartment", 34.0718550d, -118.3814818d, 20.0f, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT));
        basicGeofences.add(new BasicGeofence("2", "Eli's House", 34.045802d, -118.391353d, 30.0f, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT));

        basicGeofences.add(new BasicGeofence("3", "Nonnas (5)", 34.073489d, -118.379223d, 5.0f, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT));
        basicGeofences.add(new BasicGeofence("4", "Subway (8)", 34.073128d, -118.376803d, 8.0f, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT));
        basicGeofences.add(new BasicGeofence("5", "Chipotle (10)", 34.074452d, -118.376796d, 10.0f, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT));
        basicGeofences.add(new BasicGeofence("6", "Coffee Bean (15)", 34.073066d, -118.376245d, 15.0f, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT));
        basicGeofences.add(new BasicGeofence("7", "3rd Street Stattion (3)", 34.073213d, -118.375984d, 3.0f, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT));
        basicGeofences.add(new BasicGeofence("8", "CVS (30)", 34.075533d, -118.375959d, 30.0f, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT));
        basicGeofences.add(new BasicGeofence("9", "Mens Warehouse (5)", 34.074083d, -118.376372d, 5.0f, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT));
        basicGeofences.add(new BasicGeofence("10", "Einstein Bro Bagels (8)", 34.073555d, -118.376084d, 8.0f, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT));
        basicGeofences.add(new BasicGeofence("11", "Churchill (3)", 34.072846d, -118.372951d, 3.0f, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT));

        basicGeofences.add(new BasicGeofence("12", "Colgate & Hamel (15)", 34.070807d, -118.381646d, 15.0f, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT));
        basicGeofences.add(new BasicGeofence("13", "Colgate & Willamen (15)", 34.070847d, -118.380614d, 15.0f, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT));
        basicGeofences.add(new BasicGeofence("14", "Willamen + Back (15)", 34.071682d, -118.380691d, 15.0f, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT));
        basicGeofences.add(new BasicGeofence("15", "Side of Hamel (15)", 34.071860d, -118.381635d, 15.0f, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT));
        basicGeofences.add(new BasicGeofence("16", "8670 Burton (15)", 34.072055d, -118.381422d, 15.0f, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT));

        basicGeofences.add(new BasicGeofence("17", "Melrose Place (300)", 34.083656d, -118.372186d, 300.0f, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT));
        basicGeofences.add(new BasicGeofence("18", "Melrose & LA Brea (200)", 34.083425d, -118.344012d, 200.0f, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT));
        basicGeofences.add(new BasicGeofence("19", "Hollywood Bowl (500)", 34.109967d, -118.336041d, 500.0f, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT));
        basicGeofences.add(new BasicGeofence("20", "John Burroughs (100)", 34.168873d, -118.325698d, 100.0f, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT));

    }

    public BasicGeofence get(int i) {
        if(i < 1 || i > basicGeofences.size()) {
            return null;
        }

        // 1-based index
        return basicGeofences.get(i-1);
    }

    @Override
    public List<Geofence> buildGeofences() {

        Collection<Geofence> geofences = Collections2.transform(basicGeofences, new Function<BasicGeofence, Geofence>() {
            @Override
            public Geofence apply(BasicGeofence input) {
            return input.toGeofence();
            }
        });

        // TODO: For now until we know what we are going to do with them
        List<Geofence> returnList = new ArrayList<Geofence>();
        returnList.addAll(geofences);

        return returnList;
    }

    @Override
    public List<String> getGeofenceIds() {
        Collection<String> geofences = Collections2.transform(basicGeofences, new Function<BasicGeofence, String>() {
            @Override
            public String apply(BasicGeofence input) {
            return input.getId();
            }
        });

        // TODO: For now until we know what we are going to do with them
        List<String> returnList = new ArrayList<String>();
        returnList.addAll(geofences);

        return returnList;
    }

    @Override
    public void onAddGeofencesResult(int status, String[] strings) {
        switch(status) {
            case LocationStatusCodes.SUCCESS:
                Log.d(Constants.LOG_TAG, "Add geofences success. ");
                break;
            case LocationStatusCodes.GEOFENCE_NOT_AVAILABLE:
                Log.d(Constants.LOG_TAG, "Unable to add geofences. Not available.");
                break;
            case LocationStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                Log.d(Constants.LOG_TAG, "Unable to add geofences. Too many geofences. ");
                break;
            case LocationStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                Log.d(Constants.LOG_TAG, "Unable to add geofences. Too many intents.");
                break;
            default:
                // mainly LocationStatusCodes.ERROR
                Log.d(Constants.LOG_TAG, "Add geofences error. (" + status + ")");
        }
        Log.d(Constants.LOG_TAG, Arrays.toString(strings));
    }

    @Override
    public void onRemoveGeofencesByRequestIdsResult(int status, String[] strings) {
        switch(status) {
            case LocationStatusCodes.SUCCESS:
                Log.d(Constants.LOG_TAG, "Remove geofences success. ");
                break;
            case LocationStatusCodes.GEOFENCE_NOT_AVAILABLE:
                Log.d(Constants.LOG_TAG, "Unable to remove geofences. Not available.");
                break;
            default:
                // mainly LocationStatusCodes.ERROR
                Log.d(Constants.LOG_TAG, "Remove geofences error. (" + status + ")");
        }
        Log.d(Constants.LOG_TAG, Arrays.toString(strings));
    }

    @Override
    public void onRemoveGeofencesByPendingIntentResult(int status, PendingIntent pendingIntent) {
        Log.d(Constants.LOG_TAG, "Remove geofences pending intent result (" + status + ")");
    }
}
