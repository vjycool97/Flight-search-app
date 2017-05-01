package com.ssatyacc.ixigo.ixigoflights.utils;

import com.ssatyacc.ixigo.ixigoflights.model.Airlines;

import java.util.HashMap;

/**
 * Created by vijay on 4/4/17.
 */

public class AirlineUtils {

    public static HashMap<String, Airlines> airlines = new HashMap<>();

    static {
        airlines.put("SG", new Airlines("SG", "Spice Jet", 0));
        airlines.put("AI", new Airlines("AI", "Air India", 0));
        airlines.put("G8", new Airlines("G8", "Go Air", 0));
        airlines.put("9W", new Airlines("9W", "Jet Airways", 0));
        airlines.put("6E", new Airlines("6E", "Indigo", 0));
    }
}
