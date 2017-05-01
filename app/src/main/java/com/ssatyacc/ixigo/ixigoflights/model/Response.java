package com.ssatyacc.ixigo.ixigoflights.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vijay on 4/4/17.
 */
public class Response implements Serializable{

    private final Appendix appendix;
    private final ArrayList<Flight> flights = new ArrayList<>();

    public Appendix getAppendix() {
        return appendix;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public Response(JSONObject response) throws JSONException {
        appendix = new Appendix(response.getJSONObject("appendix"));
        JSONArray flightsJSONArray = response.getJSONArray("flights");

        for (int i = 0; i < flightsJSONArray.length(); i++) {
            JSONObject flightObject = (JSONObject) flightsJSONArray.get(i);
            Flight flight = Flight.create(flightObject);
            flights.add(flight);
        }
    }
}