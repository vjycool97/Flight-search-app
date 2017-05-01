package com.ssatyacc.ixigo.ixigoflights.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by vijay on 4/4/17.
 */
public class Appendix implements Serializable{
    private static final String TAG = "Appendix";
    private final HashMap<String, String> airlineCodeMapping = new HashMap<>();
    private final HashMap<String, String> airportCodeMapping = new HashMap<>();
    private final HashMap<String, String> providersMapping = new HashMap<>();

    Appendix(JSONObject appendix) throws JSONException {
        setAirlineCodeMapping(appendix.getJSONObject("airlines"));
        setAirportCodeMapping(appendix.getJSONObject("airports"));
        setProvidersMapping(appendix.getJSONObject("providers"));
    }

    public HashMap<String, String> getAirlineCodeMapping() {
        return airlineCodeMapping;
    }

    private void setAirlineCodeMapping(JSONObject airlineCodes) throws JSONException {
        Iterator<String> iterator = airlineCodes.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = airlineCodes.getString(key);
            airlineCodeMapping.put(key, value);
        }
    }

    public HashMap<String, String> getAirportCodeMapping() {
        return airportCodeMapping;
    }

    private void setAirportCodeMapping(JSONObject airportCodes) throws JSONException {
        Iterator<String> iterator = airportCodes.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = airportCodes.getString(key);
            airportCodeMapping.put(key, value);
        }
    }

    public HashMap<String, String> getProvidersMapping() {
        return providersMapping;
    }

    private void setProvidersMapping(JSONObject providers) throws JSONException {
        Iterator<String> iterator = providers.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = providers.getString(key);
            providersMapping.put(key, value);
        }
    }
}
