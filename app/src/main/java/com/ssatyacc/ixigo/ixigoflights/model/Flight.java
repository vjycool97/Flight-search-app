package com.ssatyacc.ixigo.ixigoflights.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by vijay on 4/4/17.
 */
public class Flight implements Serializable {
    private static final String TAG = "Flight";

    private final String originCode;
    private final String destinationCode;
    private final long departureTime;
    private final long arrivalTime;
    private final ArrayList<Fare> fares;
    private Fare minFare;
    private final String airlineCode;
    private final String seat;

    private Flight(String originCode, String destinationCode, long departureTime, long arrivalTime, ArrayList<Fare> fares, String airlineCode, String seat) {
        this.originCode = originCode;
        this.destinationCode = destinationCode;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.fares = fares;
        this.airlineCode = airlineCode;
        this.seat = seat;

        for (Fare fare : fares) {
            if (minFare == null || minFare.getFare() > fare.getFare()) {
                minFare = fare;
            }
        }
    }

    public String getOriginCode() {
        return originCode;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public long getDepartureTime() {
        return departureTime;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public ArrayList<Fare> getFares() {
        return fares;
    }

    public Fare getMinFare() {
        return minFare;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public String getSeat() {
        return seat;
    }

    public static Flight create(JSONObject fO) throws JSONException {
        return new Flight(fO.getString("originCode"), fO.getString("destinationCode"), fO.getLong("departureTime"), fO.getLong("arrivalTime"), Fare.create(fO.getJSONArray("fares")), fO.getString("airlineCode"), fO.getString("class"));
    }
}