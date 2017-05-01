package com.ssatyacc.ixigo.ixigoflights.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vijay on 4/4/17.
 */
public class Fare implements Serializable{
    private final long providerId;
    private final long fare;

    private Fare(long providerId, long fare) {
        this.providerId = providerId;
        this.fare = fare;
    }

    public long getProviderId() {
        return providerId;
    }

    public long getFare() {
        return fare;
    }

    public static ArrayList<Fare> create(JSONArray faresJSON) throws JSONException {
        ArrayList<Fare> fares = new ArrayList<>();
        for (int i = 0; i < faresJSON.length(); i++) {
            JSONObject fareObject = faresJSON.getJSONObject(i);
            fares.add(new Fare(fareObject.getLong("providerId"), fareObject.getLong("fare")));
        }
        return fares;
    }
}