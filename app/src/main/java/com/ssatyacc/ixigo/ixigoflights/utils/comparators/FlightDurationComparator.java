package com.ssatyacc.ixigo.ixigoflights.utils.comparators;

import com.ssatyacc.ixigo.ixigoflights.model.Flight;

import java.util.Comparator;

/**
 * Created by vijay on 4/4/17.
 */
public class FlightDurationComparator implements Comparator<Flight> {
    @Override
    public int compare(Flight o1, Flight o2) {
        long durationOfO1 = o1.getArrivalTime() - o1.getDepartureTime();
        long durationOfO2 = o2.getArrivalTime() - o2.getDepartureTime();
        if (durationOfO1 == durationOfO2) {
            return 0;
        } else if (durationOfO1 > durationOfO2) {
            return 1;
        }
        return -1;
    }
}
